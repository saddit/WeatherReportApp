/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import com.thread0.weather.data.constant.PARAM_LOCATION
import com.thread0.weather.databinding.ActivityCarRestrictedInfoBinding
import com.thread0.weather.net.service.LifeService
import com.thread0.weather.net.service.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch

/**
 *@ClassName: CarRestrictedInfoActivity
 *@Description: 机动车限行信息页面
 * TODO：1、获取传递进来的城市，需显示
 *       2、通过城市请求限行相关信息，并展示
 *          如：处罚规定、区域与时间、详细说明、限行的具体信息。
 *@Author: hongzf
 *@Date: 2021/6/2 10:59 下午 Created
 */
class CarRestrictedInfoActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivityCarRestrictedInfoBinding
    private lateinit var cityId: String
    private val lifeService: LifeService = ScaffoldConfig.getRepositoryManager().obtainRetrofitService(
        LifeService::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarRestrictedInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cityId = intent.getStringExtra(PARAM_LOCATION)?: ""
        // 设置点击事件
        setClickEvent()
        launch(Dispatchers.IO,{
            initData()
        },{
            it.printStackTrace()
        })
    }

    private suspend fun initData() {
        val restriction =
            lifeService.getCityCarRestriction(location = cityId)!!.results[0].restriction
        withContext(Dispatchers.Main) {
            binding.info = restriction
        }
    }


    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}