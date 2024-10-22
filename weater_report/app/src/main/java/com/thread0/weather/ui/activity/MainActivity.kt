/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.ArraySet
import android.util.Log
import android.view.ActionMode
import android.view.Gravity
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.internal.ParcelableSparseArray
import com.huawei.hms.utils.UIUtil
import com.tencent.mmkv.MMKV
import com.thread0.weather.app.AppDatabase
import com.thread0.weather.data.constant.LOCATION_SAVE_KEY
import com.thread0.weather.data.constant.PERM_LOCATION_REQ_CODE
import com.thread0.weather.data.constant.SEARCH_REQ_CODE
import com.thread0.weather.data.constant.SEARCH_RES_KEY
import com.thread0.weather.data.dao.CityDao
import com.thread0.weather.data.model.City
import com.thread0.weather.data.model.Location
import com.thread0.weather.data.model.Weather
import com.thread0.weather.databinding.ActivityMainBinding
import com.thread0.weather.net.service.WeatherService
import com.thread0.weather.ui.adapter.MainFragmentPagerAdapter
import com.thread0.weather.ui.fragment.WeatherFragment
import com.thread0.weather.util.AMapLocationUtils
import com.thread0.weather.util.PinyinUtils
import com.thread0.weather.util.UiUtils
import kotlinx.coroutines.*
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import top.xuqingquan.utils.startActivity
import top.xuqingquan.utils.startActivityForResult
import java.util.*
import java.util.concurrent.ConcurrentSkipListSet
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet

/**
 *@ClassName: MainActivity
 *@Description:       1、进入界面首先获取已经保存的城市列表
 *                    2、未获取到列表需通过定位获取用户当前位置
 *                    3、获取用户定位前需判断用户是否授予定位权限
 *                    4、未授予定位权限需申请，申请后被拒绝需添加默认城市进入关注列表，并弹窗提示用户未未授予权限
 *                    5、获取到城市列表或刷新城市列表后，根据当前选择的城市展示天气数据
 *                    6、需要有按钮可以进入SearchActivity，添加城市进城市列表,添加完毕后需要刷新城市列表，并选择新添加的城市为当前选中的城市
 *                    7、通过左右滑动去切换当前选择城市，使用viewPager（viewPager2也可以）+Fragment（展示天气数据）实现
 *                    8、Fragment展示的天气数据：
 *                        8.1、当前气温
 *                        8.2、当前天气
 *                        8.3、月升月落时间
 *                        8.4、日升日落时间
 *                        8.5、展示未来7天气天气预报，7日天气预报尾部添加一个按钮进入FutureWeatherActivity界面
 *                    9、Fragment内一些必须的按钮：
 *                        9.1、进入AirQualityActivity的按钮,按钮文字——空气质量
 *                        9.2、进入YesterdayActivity的按钮，按钮文字——昨日天气
 *                        9.3、进入AlarmActivity的按钮，按钮文字——气象预警
 *                        9.4、进入ZodiacActivity的按钮，按钮文字——农历、节气与生肖
 *                        9.5、进入PortActivity的按钮，按钮文字——港口查询,判断当前fragment城市为港口城市则此按钮显示，不为港口城市则不显示
 *                        9.6、进入CarRestrictedCityActivity的按钮，按钮文字——机动车尾号限行
 *                        9.7、点击当前温度以及天气进入HoursActivity界面
 *                    10、需要有按钮点击进入HmsActivity
 *@Date: 2021/5/25 11:36 下午 Created
 */
class MainActivity : SimpleActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var locations: LinkedList<String>

    private val locationObjects: LinkedList<City> = LinkedList()

    private val weatherService: WeatherService = ScaffoldConfig.getRepositoryManager().obtainRetrofitService(
        WeatherService::class.java
    )

    private val cityDao = AppDatabase.instance!!.getCityDao()

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initEvent()
        loadLocations()
        launch(Dispatchers.IO,{
            initPager()
        },{
          it.printStackTrace()
        })
    }

    private fun loadLocations() {
        locations = LinkedList()
        val locationNames = MMKV.defaultMMKV()!!.decodeString(LOCATION_SAVE_KEY);
        if (locationNames != null) {
            Log.i("sjh_main_activity", "locations init from MMKV decode:$locationNames")
            locations.addAll(locationNames.split(","))
        } else {
            Log.i("sjh_main_activity", "locations init from AMap")
            requestLocatePerm()
        }
    }

    private fun requestLocatePerm() {
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERM_LOCATION_REQ_CODE)
        } else {
            useAMapLocate()
        }
    }

    private fun useAMapLocate() {
        AMapLocationUtils.getInstance().startLocation()
        AMapLocationUtils.getInstance().liveData.observe(this, {
            AMapLocationUtils.getInstance().stopLocation()
            AMapLocationUtils.getInstance().destroyLocation()
            launch(Dispatchers.IO,{
                val afterReplace = it.district.replace(Regex("[区县市]+$"), "")
                Log.i("sjh_main_ac", "AMap located district $afterReplace")
                val phonetic = PinyinUtils.translate(afterReplace)
                val city = cityDao.queryByPhonetic(phonetic)
                Log.i("sjh_main_ac", "city queried $city")
                addPage(city)
            },{
                Log.e("sjh_main_ac", it.stackTraceToString())
            })
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERM_LOCATION_REQ_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                UiUtils.showToast("定位权限获取失败，请手动添加地址", gravity = Gravity.CENTER)
            } else {
                useAMapLocate()
            }
        }
    }


    override fun onStop() {
        MMKV.defaultMMKV()!!.encode(LOCATION_SAVE_KEY, locations.joinToString(","))
        Log.i("sjh_main_activity", "[onStop] MMKV encode $locations")
        super.onStop()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        if(requestCode == SEARCH_REQ_CODE) {
            val city: City = data.getSerializableExtra(SEARCH_RES_KEY) as City
            if (locations.contains(city.code)) {
                UiUtils.showToast("重复了哦",gravity = Gravity.CENTER)
                return
            }
            launch(Dispatchers.IO,{
                addPage(city)
            },{
                Log.e("sjh_main_ac", it.stackTraceToString())
            })
        }
    }

    private fun initEvent() {
        binding.goHmsBtn.setOnClickListener {
            startActivity<HmsActivity>()
        }
        binding.addCityBtn.setOnClickListener{
            startActivityForResult<SearchActivity>(SEARCH_REQ_CODE)
        }
        binding.mainTitle.setOnClickListener{
            AlertDialog.Builder(this)
                .setMessage("确认删除这个地点吗？")
                .setTitle("移除地址")
                .setPositiveButton("确认") { i,v->
                    val adapter = binding.weatherViewPager.adapter!!
                    if(adapter.itemCount == 1){
                        UiUtils.showToast("至少保留一个页面", gravity = Gravity.CENTER)
                    } else {
                        removeFragment()
                    }
                }.create().show()
        }
    }

    private fun removeFragment() {
        val currentItem = binding.weatherViewPager.currentItem
        (binding.weatherViewPager.adapter as MainFragmentPagerAdapter).removeFragment(currentItem)
        locations.removeAt(currentItem)
        locationObjects.removeAt(currentItem)
        thisOnPageSelected(if(currentItem == locations.size) currentItem-1 else currentItem)
    }

    private suspend fun addPage(location: City?) {
        if(location == null) return
        val result = weatherService.getLocationCurrentWeather(location.code)!!.results[0]
        locationObjects.add(location)
        locations.add(location.code)
        withContext(Dispatchers.Main) {
            val adapter = binding.weatherViewPager.adapter
            (adapter as MainFragmentPagerAdapter).addFragment(WeatherFragment.newInstance(result))
            binding.weatherViewPager.currentItem = adapter.itemCount-1
        }
    }

    private fun thisOnPageSelected(position: Int) {
        val city = locationObjects[position]
        binding.mainTitle.text = city.name
    }

    private fun initUI() {
        binding.weatherViewPager.adapter = MainFragmentPagerAdapter(supportFragmentManager, lifecycle, arrayListOf())
        binding.weatherViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                thisOnPageSelected(position)
            }
        })
    }

    private suspend fun initPager() {
        val fragments = mutableListOf<Fragment>()
        for (location in locations) {
            val result = weatherService.getLocationCurrentWeather(location)!!.results[0]
            fragments.add(WeatherFragment.newInstance(result))
            launch(Dispatchers.IO,{
                val city = cityDao.queryByCode(result.location.id)!!
                locationObjects.add(city)
            },{
                Log.e("sjh_main_ac", it.stackTraceToString())
            })
        }
        withContext(Dispatchers.Main){
            (binding.weatherViewPager.adapter as MainFragmentPagerAdapter).addFragmentRange(fragments)
        }
    }
}