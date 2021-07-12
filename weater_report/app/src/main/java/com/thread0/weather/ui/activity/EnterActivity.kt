/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import com.thread0.weather.databinding.ActivityEnterBinding
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.utils.startActivity

/**
 *@ClassName: EnterActivity
 *@Description: 欢迎界面
 *@Author: hongzf
 *@Date: 2021/5/27 11:18 下午 Created
 */
class EnterActivity : SimpleActivity() {

    // view binding----https://blog.csdn.net/c10WTiybQ1Ye3/article/details/112690188
    private lateinit var binding: ActivityEnterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //TODO:引导界面，同学可自行丰富。
        parseXMLIntoDB()
    }

    /**
     * TODO:1、使用jxl(已依赖)解析excel文件获取城市列表以及港口城市列表并保存到数据库；
     *      2、做好异常处理；
     *      3、保存完毕后跳转MainActivity，并销毁当前页面。
     */
    private fun parseXMLIntoDB() {


        //操作完成后跳转首页
        startActivity<MainActivity>()
        finish()
    }
}