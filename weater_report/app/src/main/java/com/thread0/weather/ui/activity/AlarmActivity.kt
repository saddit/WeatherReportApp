/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import com.thread0.weather.databinding.ActivityAlarmBinding
import top.xuqingquan.base.view.activity.SimpleActivity

/**
*@ClassName: AlarmActivity
*@Description: 预警页面
 * TODO:1、通过传递进来的城市数据获取对应的天气预警数据,并通过RecycleView展示
 *      2、当前界面需要显示当前城市名称
*@Author: hongzf
*@Date: 2021/5/31 9:43 下午 Created
*/
class AlarmActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
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