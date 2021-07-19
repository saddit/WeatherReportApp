/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.data.model.AirQuality
import com.thread0.weather.data.model.Location
import com.thread0.weather.databinding.ActivityAirQualityBinding
import com.thread0.weather.net.service.AirQualityService
import com.thread0.weather.ui.adapter.DailyAqiAdapter
import com.thread0.weather.ui.adapter.HourlyAqiAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import top.xuqingquan.utils.startActivity

/**
 *@ClassName: AirQualityActivity
 *@Description: 空气质量页面
 * TODO：1、获取
 *          1.1、当前空气质量
 *          1.2、逐日空气质量
 *          1.3、逐小时空气质量
 *          1.4、历史逐小时空气质量
 *          按重要程度进行展示，如布局所示使用NestedScrollView包括展示以上数据的各个view，使其可以滚动展示大量数据
 *      2、当然在获取这些数据前需要获取传递进来的城市，并在界面上显示
 *      3、界面中需要放置一个进入AirQualityRankActivity界面的按钮，按钮文字——空气质量排行榜
 *@Author: hongzf
 *@Date: 2021/6/2 10:58 下午 Created
 */
class AirQualityActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivityAirQualityBinding
    private lateinit var location: Location
    private lateinit var quality: AirQuality
    private lateinit var dailyQuality: List<AirQuality>
    private lateinit var hourlyQuality: List<AirQuality>
    private var locationParam: String? = null

    private val qualityService: AirQualityService = ScaffoldConfig.getRepositoryManager().obtainRetrofitService(
        AirQualityService::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirQualityBinding.inflate(layoutInflater)
        // 设置点击事件
        setClickEvent()
        locationParam = intent.getStringExtra("location")
        if(locationParam != null) {
            launch(Dispatchers.IO,{
                initCurrent()
                initHourly()
                initDaily()
            },{
                it.printStackTrace()
            })
        }
        setContentView(binding.root)
    }

    private suspend fun initCurrent() {
        val res = qualityService.getCurrentAirQuality(location = locationParam)!!.results[0]
        this.location = res.location
        this.quality = res.air.city
        withContext(Dispatchers.Main) {
            binding.location = location
            binding.quality = quality
        }
    }

    private suspend fun initHourly() {
        val res = qualityService.getHourlyAirQuality(location = locationParam)!!.results[0]
        this.hourlyQuality = res.multi
        withContext(Dispatchers.Main) {
            binding.rvHourlyAqi.adapter = HourlyAqiAdapter(hourlyQuality)
        }
    }

    private suspend fun initDaily() {
        val res = qualityService.getDailyAirQuality(location = locationParam)!!.results[0]
        this.dailyQuality = res.multi
        withContext(Dispatchers.Main) {
            binding.rvDailyAqi.adapter = DailyAqiAdapter(dailyQuality)
        }
    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.rankBtn.setOnClickListener {
            startActivity<AirQualityRankActivity>()
        }
    }
}