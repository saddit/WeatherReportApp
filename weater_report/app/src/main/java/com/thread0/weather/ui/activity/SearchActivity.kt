/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.thread0.weather.app.AppDatabase
import com.thread0.weather.data.constant.SEARCH_RES_KEY
import com.thread0.weather.data.model.City
import com.thread0.weather.databinding.ActivitySearchBinding
import com.thread0.weather.ui.adapter.CityListAdapter
import com.thread0.weather.util.UiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import top.xuqingquan.utils.toast
import kotlin.math.log

/**
 *@ClassName: SearchActivity
 *@Description: 城市搜索页面 TODO：界面UI自行美化
 *@Author: hongzf
 *@Date: 2021/6/2 11:00 下午 Created
 */
class SearchActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivitySearchBinding
    private val cityDao = AppDatabase.instance!!.getCityDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launch(Dispatchers.IO,{
            // 初始化数据与布局
            initDataAndUI()
        },{
            it.printStackTrace()
        })
    }

    private suspend fun initDataAndUI() {
        val cityList = cityDao.queryLimit(50)
        Log.i("sjh_search_ac", "query cities result ${cityList.size}")
        withContext(Dispatchers.Main) {
            binding.rvCityList.adapter = CityListAdapter(cityList)
            setClickEvent()
        }
    }

    private suspend fun searchCity(keyword: String) {
        val cityList: List<City> = if(keyword.isEmpty()) {
            cityDao.queryLimit(50)
        } else {
            cityDao.queryByKeyword("%$keyword%")
        }
        Log.i("sjh_search_ac", "search cities result ${cityList.size}")
        withContext(Dispatchers.Main) {
            (binding.rvCityList.adapter as CityListAdapter).setData(cityList)
            if (cityList.isEmpty()) {
                UiUtils.showToast("找不到该城市，换个词搜索吧", Gravity.CENTER);
            }
        }
    }

    private fun returnResult(city:City) {
        val intent = Intent()
        Log.i("sjh_search_ac","return result $city")
        intent.putExtra(SEARCH_RES_KEY, city)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun setClickEvent() {
        // 返回
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        (binding.rvCityList.adapter as CityListAdapter).setOnClickCityListener { _, c, _ ->
            returnResult(c)
        }
        // 文字变化时刷新列表
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                refreshCityList(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    /**
     * TODO:依据关键词进行城市列表的刷新，保留与用户关键词相关的城市
     *      tips：当前关键词搜索不到结果时，需要提醒用户。
     * @param keyword 关键词
     */
    fun refreshCityList(keyword: String) {
        launch(Dispatchers.IO,{
            searchCity(keyword)
        },{
            it.printStackTrace()
        })
    }
}