/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import android.util.Log
import com.thread0.weather.app.AppDatabase
import com.thread0.weather.data.constant.PARAM_LOCATION
import com.thread0.weather.data.model.Port
import com.thread0.weather.databinding.ActivityPortBinding
import com.thread0.weather.ui.adapter.PortListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch

/**
*@ClassName: PortActivity
*@Description: 港口城市页面
 * TODO:通过传递进来的城市信息，查询港口信息，并通过RecycleView展示
*@Author: hongzf
*@Date: 2021/6/2 10:59 下午 Created
*/
class PortActivity : SimpleActivity() {

    private lateinit var binding: ActivityPortBinding

    private lateinit var portList: List<Port>

    private val portDao = AppDatabase.instance!!.getPortDao()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 设置点击事件
        setClickEvent()
        val locationId = intent.getStringExtra(PARAM_LOCATION)
        if(locationId != null) {
            launch(Dispatchers.IO,{
                initData(locationId)
            },{
                Log.e("sjh_port_ac", it.stackTraceToString())
            })
        }
    }

    private suspend fun initData(id:String) {
        portList = portDao.queryByCityCode(id)
        withContext(Dispatchers.Main) {
            initUI()
        }
    }

    private fun initUI() {
        binding.recyclerView.adapter = PortListAdapter(portList)
    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

}