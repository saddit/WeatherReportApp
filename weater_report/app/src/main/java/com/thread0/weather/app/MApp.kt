/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.app

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import com.thread0.weather.net.WEATHER_URL
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.delegate.AppDelegate
import top.xuqingquan.delegate.AppLifecycle
import top.xuqingquan.http.log.Level

/**
*@ClassName: MApp
*@Description: Application
 * TODO：res文件夹内有一些图片，开发过程中可以使用，页可以自己寻找更好看的图片，无用图片需在最后提交前删除干净
*@Author: hongzf
*@Date: 2021/5/25 11:35 下午 Created
*/
class MApp: Application() {

    private lateinit var mAppDelegate: AppLifecycle

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        mAppDelegate = AppDelegate.getInstance(base!!)
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        //生命周期初始化
        mAppDelegate.onCreate(this)
        //配置初始化
        ScaffoldConfig.getInstance(this)
            .setBaseUrl(WEATHER_URL)
            .setLevel(Level.NONE)
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate.onTerminate(this)
    }

}