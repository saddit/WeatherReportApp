/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import com.thread0.weather.databinding.ActivityPortBinding
import top.xuqingquan.base.view.activity.SimpleActivity

/**
*@ClassName: PortActivity
*@Description: 港口城市页面
 * TODO:通过传递进来的城市信息，查询港口信息，并通过RecycleView展示
*@Author: hongzf
*@Date: 2021/6/2 10:59 下午 Created
*/
class PortActivity : SimpleActivity() {

    private lateinit var binding: ActivityPortBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortBinding.inflate(layoutInflater)
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