/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import android.util.Log
import com.thread0.weather.data.model.Location
import com.thread0.weather.data.model.Weather
import com.thread0.weather.databinding.ActivityYesterdayBinding
import com.thread0.weather.net.service.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import java.lang.IllegalStateException

/**
 *@ClassName: YesterdayActivity
 *@Description: 昨日天气页面
 *TODO：1、通过传递进来的城市数据获取对应的昨日天气数据并展示出来
 *      2、当前界面需要显示当前城市名称、日期（天气数据的日期）
 *      3、自行思考如何布局，把用户最关心的数据显眼的展示到界面上
 *@Author: hongzf
 *@Date: 2021/6/2 11:00 下午 Created
 */
class YesterdayActivity : SimpleActivity() {

    private lateinit var weather: Weather
    private lateinit var location: Location

    private val weatherService: WeatherService = ScaffoldConfig.getRepositoryManager().obtainRetrofitService(
        WeatherService::class.java
    )
    // view binding
    private lateinit var binding: ActivityYesterdayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYesterdayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 设置点击事件
        setClickEvent()
        onIntent()
    }

    private fun onIntent() {
        val loca: String = intent.getStringExtra("location") ?: return
        launch(Dispatchers.IO,{
            initWeather(loca)
        },{
            it.printStackTrace();
        })
    }

    private suspend fun initWeather(loca: String) {
        val res = loca.let {
            weatherService.getDailyWeather(location = it, start = -1, days = 1)!!.results[0]
        }
        weather = res.multi[0]
        location = res.location
        withContext(Dispatchers.Main) {
            binding.weather = weather
            binding.location = location
        }
    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}