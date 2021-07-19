/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.data.model.AirQualityRank
import com.thread0.weather.data.model.Location
import com.thread0.weather.databinding.ActivityAirQualityRankBinding
import com.thread0.weather.net.service.AirQualityService
import com.thread0.weather.ui.adapter.RankAqiAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.extension.launch
import kotlin.streams.toList

/**
 *@ClassName: AirQualityRankActivity
 *@Description: 空气质量排行页面
 *  TODO:获取空气质量排名数据，并通过RecycleView展示
 *@Author: hongzf
 *@Date: 2021/6/2 10:58 下午 Created
 */
class AirQualityRankActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAirQualityRankBinding

    private val qualityService: AirQualityService = ScaffoldConfig.getRepositoryManager().obtainRetrofitService(
        AirQualityService::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirQualityRankBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 设置点击事件
        setClickEvent()

        launch(Dispatchers.IO,{
            initRank()
        },{
            it.printStackTrace()
        })
    }

    private suspend fun initRank() {
        val airQualityRank = qualityService.getAirQualityRank()!!.results
        withContext(Dispatchers.Main) {
            binding.rvRankAqi.adapter = RankAqiAdapter(airQualityRank)
        }
    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

}