/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import com.thread0.weather.databinding.ActivityHoursBinding
import top.xuqingquan.base.view.activity.SimpleActivity

/**
 *@ClassName: HoursActivity
 *@Description: 24 小时预报页面
 * TODO：1、获取传递进这个界面的城市
 *      2、通过城市获取小时天气，并展示
 *@Author: hongzf
 *@Date: 2021/5/30 10:35 下午 Created
 */
class HoursActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivityHoursBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHoursBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}