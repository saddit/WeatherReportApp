/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.util

import java.text.SimpleDateFormat
import java.util.Locale

/**
*@ClassName: TimeUtils
*@Description: 时间处理工具类
*@Author: hongzf
*@Date: 2021/5/27 10:59 下午 Created
*/
object TimeUtils {

    /**
     * getUnixTimestamp 获取 unix 时间戳 秒
     * @return unix 时间戳
     */
    fun getUnixTimestamp(): Long{
        return System.currentTimeMillis()/1000
    }

    /**
     * 根据需求将数字时间（毫秒/秒）转为对应规则的日期
     *
     * @param time    毫秒/秒值
     * @param pattern 规则：例如："yyyy-MM-dd HH:mm:ss"
     * @return 字符串时间
     */
    fun time2String(time: Long, pattern: String?): String? {
        var timeVar = time
        if (timeVar.toString().length < 12) { //秒
            timeVar *= 1000
        }
        return SimpleDateFormat(pattern, Locale.getDefault()).format(timeVar)
    }

    fun getCurrentHour(): String{
        return SimpleDateFormat("HH",Locale.getDefault()).format(System.currentTimeMillis())
    }

    fun getTodayDate(): String{
        return SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(System.currentTimeMillis())
    }

    fun getTomorrowDate(): String{
        return SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(System.currentTimeMillis()+24*60*60*1000)
    }

    fun getDayAfterTomorrowDate(): String{
        return SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(System.currentTimeMillis()+24*60*60*1000*2)
    }

    fun getWeekByDate(date: String): String{
        val time = SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).parse(date)
        return SimpleDateFormat("EEEE",Locale.CHINA).format(time)
    }

    fun getYesterdayDate(): String{
        return SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(System.currentTimeMillis()-24*60*60*1000)
    }

    fun getFormatTime(timeString: String): String{
        val format = timeString.substringBefore("+")
        val date = format.substringBefore("T")
        val time = format.substringAfter("T")
        return "$date $time"
    }
}