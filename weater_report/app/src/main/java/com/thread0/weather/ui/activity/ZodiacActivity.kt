/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import android.util.Log
import com.thread0.weather.data.model.Calendar
import com.thread0.weather.databinding.ActivityZodiacBinding
import com.thread0.weather.net.service.LifeService
import com.thread0.weather.net.service.WeatherService
import com.thread0.weather.ui.adapter.ZodiacRvAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch

/**
 *@ClassName: ZodiacActivity
 *@Description: 农历、节气与生肖页面
 *TODO:获取农历、节气与生肖的数据，自行考虑如何展示，获取的数据需要全部展示出来
 *@Author: hongzf
 *@Date: 2021/6/2 11:00 下午 Created
 */
class ZodiacActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivityZodiacBinding

    private val lifeService: LifeService = ScaffoldConfig.getRepositoryManager().obtainRetrofitService(
        LifeService::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZodiacBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launch(Dispatchers.IO, {
            initData()
        },{
            Log.e("sjh_zodiac_ac", it.stackTraceToString())
        })
        // 设置点击事件
        setClickEvent()
        // 初始化数据与布局
    }

    private suspend fun initData() {
        val chineseCalendar = lifeService.getChineseCalendar()
        if(chineseCalendar != null) {
            withContext(Dispatchers.Main) {
                initUI(chineseCalendar.results.chineseCalendar)
            }
        }
    }

    private fun initUI(calendars: List<Calendar>) {
        binding.zodiacList.adapter = ZodiacRvAdapter(calendars);
    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }


}