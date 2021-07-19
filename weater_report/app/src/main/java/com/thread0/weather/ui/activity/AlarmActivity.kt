/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.data.constant.PARAM_LOCATION
import com.thread0.weather.data.model.Alarms
import com.thread0.weather.databinding.ActivityAlarmBinding
import com.thread0.weather.net.service.WeatherService
import com.thread0.weather.ui.adapter.AlarmAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch

/**
 *@ClassName: AlarmActivity
 *@Description: 预警页面
 * 1、通过传递进来的城市数据获取对应的天气预警数据,并通过RecycleView展示
 * 2、当前界面需要显示当前城市名称
 *@Author: hongzf
 *@Date: 2021/5/31 9:43 下午 Created
 */
class AlarmActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var mBeanList:List<Alarms>
    private val weatherService =
        ScaffoldConfig.getRepositoryManager().obtainRetrofitService(WeatherService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 设置点击事件
        setClickEvent()
        launch(Dispatchers.IO, {
            initData()
        },{
            it.printStackTrace()
        })
    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private suspend fun initData() {
        val stringExtra = intent.getStringExtra(PARAM_LOCATION)
        val alarmResult =
            weatherService.getLocationCurrentAlarm(location = stringExtra!!)!!.results[0]
        mBeanList = alarmResult.alarms
        withContext(Dispatchers.Main) {
            initView()
        }
    }

    private fun initView() {
        binding.recyclerView.adapter = AlarmAdapter(mBeanList)
        if (mBeanList.isEmpty()) {
            binding.tvTip.visibility = View.VISIBLE
        }
    }

}