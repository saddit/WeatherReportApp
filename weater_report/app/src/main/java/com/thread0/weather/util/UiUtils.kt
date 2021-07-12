/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.util

import android.app.Activity

/**
*@ClassName: UiUtils
*@Description: 界面与交互工具类
*@Author: hongzf
*@Date: 2021/5/28 8:36 下午 Created
*/
object UiUtils {

    private var lastClickTime: Long = 0 //上次点击的时间
    private const val spaceTime = 500 //时间间隔

    /**
     * isFastClick 是否是快速点击
     * @return true 是 false 不是
     */
    fun isFastClick(): Boolean {
        val currentTime = System.currentTimeMillis() //当前系统时间
        val isFastClick: Boolean //是否是快速点击
        val interval = currentTime - lastClickTime
        if (interval in 0..spaceTime) {
            isFastClick = true
        } else {
            lastClickTime = currentTime
            isFastClick = false
        }
        return isFastClick
    }

    fun activityIsDestroy(activity: Activity?) : Boolean{
        return activity == null || activity.isFinishing || activity.isDestroyed
    }
}