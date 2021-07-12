/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thread0.weather.databinding.ActivityAirQualityRankBinding

/**
 *@ClassName: AirQualityRankActivity
 *@Description: 空气质量排行页面
 *  TODO:获取空气质量排名数据，并通过RecycleView展示
 *@Author: hongzf
 *@Date: 2021/6/2 10:58 下午 Created
 */
class AirQualityRankActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAirQualityRankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirQualityRankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置点击事件
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

}