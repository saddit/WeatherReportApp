/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
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
import kotlinx.coroutines.*
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import top.xuqingquan.utils.startActivity
import top.xuqingquan.utils.startActivityForResult
import java.util.*
import java.util.concurrent.ConcurrentSkipListSet

/**
 *@ClassName: MainActivity
 *@Description: TODO: 1、进入界面首先获取已经保存的城市列表
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

    private lateinit var locations: ConcurrentSkipListSet<String>

    private val locationObjects: MutableList<Location> = mutableListOf()

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
        },{})
    }

    private fun loadLocations() {
        val locationNameSet = MMKV.defaultMMKV()!!.decodeStringSet(LOCATION_SAVE_KEY);
        if (locationNameSet != null) {
            Log.i("main_activity", "locations init from MMKV")
            locations = ConcurrentSkipListSet(locationNameSet)
        } else {
            Log.i("sjh_main_activity", "locations init from AMap")
            locations = ConcurrentSkipListSet()
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
                val resultPhonetic = PinyinUtils.translate(it.district)
                Log.i("sjh_main_ac", "AMap locate $resultPhonetic")
                addPage(PinyinUtils.translate(it.district.replace(Regex("[区县市]+$"), "")))
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
                Toast.makeText(this, "定位权限获取失败，请手动添加地址", LENGTH_SHORT).show()
            } else {
                useAMapLocate()
            }
        }
    }

    override fun onDestroy() {
        MMKV.defaultMMKV()!!.encode(LOCATION_SAVE_KEY, locations)
        Log.i("sjh_main_activity", "[onDestroy] MMKV encode $locations")
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        if(requestCode == SEARCH_REQ_CODE) {
            val city: City = data.getSerializableExtra(SEARCH_RES_KEY) as City
            launch(Dispatchers.IO,{
                addPage(city.phonetic)
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
    }

    private suspend fun addPage(location: String?) {
        if(location == null) return
        val result = weatherService.getLocationCurrentWeather(location)!!.results[0]
        locationObjects.add(result.location)
        locations.add(location.toLowerCase(Locale.ROOT))
        withContext(Dispatchers.Main) {
            (binding.weatherViewPager.adapter as MainFragmentPagerAdapter).addFragment(WeatherFragment.newInstance(result))
        }
    }

    private fun initUI() {
        binding.weatherViewPager.adapter = MainFragmentPagerAdapter(supportFragmentManager, lifecycle, arrayListOf())
        binding.weatherViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val cityId = locationObjects[position].id
                launch(Dispatchers.IO,{
                    Log.i("sjh_main_ac", "viewPager selected fragment with city $cityId")
                    val city = cityDao.queryByCode(cityId)
                    Log.i("sjh_main_ac", "query by code result $city")
                    withContext(Dispatchers.Main) {
                        binding.mainTitle.text = city?.name ?: "ERROR"
                    }
                },{
                    Log.e("sjh_main_ac", it.stackTraceToString())
                })
            }
        })
    }

    private suspend fun initPager() {
        val fragments = mutableListOf<Fragment>()
        val tempList = mutableListOf<Weather>()
        for (location in locations) {
            val result = weatherService.getLocationCurrentWeather(location)!!.results[0]
            fragments.add(WeatherFragment.newInstance(result));
            locationObjects.add(result.location)
            tempList.add(result.now)
        }
        Log.i("sjh_main_ac", "get weather(${tempList.size}) $tempList")
        withContext(Dispatchers.Main){
            (binding.weatherViewPager.adapter as MainFragmentPagerAdapter).addFragmentRange(fragments)
        }
    }
}