/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import com.thread0.weather.databinding.ActivityHmsBinding
import com.thread0.weather.ui.widget.CircleDot
import top.xuqingquan.base.view.activity.SimpleActivity

/**
 * TODO:Hms界面
 *      人像分割模型包
 *      文档：https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides-V5/image-segmentation-0000001050040109-V5#ZH-CN_TOPIC_0000001050990259__section1658976113112
 */
class HmsActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivityHmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置点击事件
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.photoImgIV.setOnClickListener {
            //TODO:点击前往选择图片，选择完成后显示在此
        }
        binding.redCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                //TODO：开始运用HMSCore的抠图能力，抠出人像，并设置背景色为红色后显示到photoImgIV，处理过程需要有处理中的提示
            }
        })
        binding.blueCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                //TODO：开始运用HMSCore的抠图能力，抠出人像，并设置背景色为蓝色后显示到photoImgIV，处理过程需要有处理中的提示
            }
        })
        binding.whiteCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                //TODO：开始运用HMSCore的抠图能力，抠出人像，并设置背景色为白色后显示到photoImgIV，处理过程需要有处理中的提示
            }
        })
    }
}