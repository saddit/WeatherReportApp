/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import android.util.Log
import com.thread0.weather.app.AppDatabase
import com.thread0.weather.data.model.City
import com.thread0.weather.data.model.Port
import com.thread0.weather.data.model.PortCity
import com.thread0.weather.databinding.ActivityEnterBinding
import com.thread0.weather.util.ExcelUtils
import com.thread0.weather.util.UiUtils
import jxl.Sheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import top.xuqingquan.utils.startActivity
import java.io.InputStream

/**
 *@ClassName: EnterActivity
 *@Description: 欢迎界面
 *@Author: hongzf
 *@Date: 2021/5/27 11:18 下午 Created
 */
class EnterActivity : SimpleActivity() {
    // view binding----https://blog.csdn.net/c10WTiybQ1Ye3/article/details/112690188
    private lateinit var binding: ActivityEnterBinding
    private val portDao = AppDatabase.instance!!.getPortDao()
    private val cityDao = AppDatabase.instance!!.getCityDao()
    private val portCityDao = AppDatabase.instance!!.getPortCityDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseXMLIntoDB()
    }

    private suspend fun savePort(ports: List<Port>) {
        ports.forEach {
            portDao.insert(it)
        }
    }

    private suspend fun saveCity(cities: List<City>) {
        cities.forEach {
            cityDao.insert(it)
        }
    }

    private suspend fun savePortCity(data: List<PortCity>) {
        data.forEach {
            portCityDao.insert(it)
        }
    }

    private suspend fun loadPortCityInfo(sheet: Sheet) {
        val res = ExcelUtils.getListFromSheet(sheet, PortCity::class.java)
        savePortCity(res)
    }

    private suspend fun loadCityInfo(inputStream: InputStream) {
        val workbook = ExcelUtils.getWorkBook(inputStream)
        val cityList = ExcelUtils.getListFromSheet(workbook.getSheet(1), City::class.java)
        saveCity(cityList)
    }

    private suspend fun loadPortInfo(inputStream: InputStream) {
        val workBook = ExcelUtils.getWorkBook(inputStream)
        val portList = ExcelUtils.getListFromSheet(workBook.getSheet(0), Port::class.java)
        savePort(portList)
        loadPortCityInfo(workBook.getSheet(1))
    }

    /**
     *      1、使用jxl(已依赖)解析excel文件获取城市列表以及港口城市列表并保存到数据库；
     *      2、做好异常处理；
     *      3、保存完毕后跳转MainActivity，并销毁当前页面。
     */
    private fun parseXMLIntoDB() {
        launch(Dispatchers.IO, {
            val portIs = assets.open("ports.xls")
            val cityIs = assets.open("cities.xls")
            loadPortInfo(portIs)
            loadCityInfo(cityIs)
        }, {
            Log.e("sjh_enter_ac", it.stackTraceToString())
            withContext(Dispatchers.Main) {
                UiUtils.showToast("港口、城市初始化失败")
            }
        }, {
            withContext(Dispatchers.Main) {
                startActivity<MainActivity>()
                finish()
            }
        })
    }
}
