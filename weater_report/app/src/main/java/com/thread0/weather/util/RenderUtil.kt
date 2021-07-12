/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.util

import android.graphics.Paint

object RenderUtil {
    fun getPaint(color: Int): Paint {
        val paint = Paint()
        paint.color = color
        //开启抗锯齿
        paint.isAntiAlias = true
        //开启防抖动
        paint.isDither = true
        return paint
    }
}