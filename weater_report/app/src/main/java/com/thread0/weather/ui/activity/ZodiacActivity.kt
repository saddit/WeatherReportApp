/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import com.thread0.weather.databinding.ActivityZodiacBinding
import top.xuqingquan.base.view.activity.SimpleActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZodiacBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置点击事件
        setClickEvent()
        // 初始化数据与布局
    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }


}