package com.thread0.weather.data.constant

import com.thread0.weather.R

/**
 *@ClassName: Sky
 *@Description: 天气图标与背景
 *@Author: hongzf
 *@Date: 2021/6/2 10:55 下午 Created
 */
data class Sky(
    val info: String,
    val icon: Int,
    val bg: Int
)

private val sky = mapOf(
    "0" to Sky("晴", R.mipmap.weather_icon_0, R.mipmap.bg_clear_day),
    "1" to Sky("晴", R.mipmap.weather_icon_1, R.mipmap.bg_clear_night),
    "2" to Sky("晴", R.mipmap.weather_icon_2, R.mipmap.bg_clear_day),
    "3" to Sky("晴", R.mipmap.weather_icon_3, R.mipmap.bg_clear_night),
    "4" to Sky("多云", R.mipmap.weather_icon_4, R.mipmap.bg_partly_cloudy_day),
    "5" to Sky("晴间多云", R.mipmap.weather_icon_5, R.mipmap.bg_partly_cloudy_day),
    "6" to Sky("晴间多云", R.mipmap.weather_icon_6, R.mipmap.bg_partly_cloudy_night),
    "7" to Sky("大部多云", R.mipmap.weather_icon_7, R.mipmap.bg_partly_cloudy_day),
    "8" to Sky("大部多云", R.mipmap.weather_icon_8, R.mipmap.bg_partly_cloudy_night),
    "9" to Sky("阴", R.mipmap.weather_icon_9, R.mipmap.bg_cloudy),
    "10" to Sky("阵雨", R.mipmap.weather_icon_10, R.mipmap.bg_rain),
    "11" to Sky("雷阵雨", R.mipmap.weather_icon_11, R.mipmap.bg_rain),
    "12" to Sky("雷阵雨伴有冰雹", R.mipmap.weather_icon_12, R.mipmap.bg_rain),
    "13" to Sky("小雨", R.mipmap.weather_icon_13, R.mipmap.bg_rain),
    "14" to Sky("中雨", R.mipmap.weather_icon_14, R.mipmap.bg_rain),
    "15" to Sky("大雨", R.mipmap.weather_icon_15, R.mipmap.bg_rain),
    "16" to Sky("暴雨", R.mipmap.weather_icon_16, R.mipmap.bg_rain),
    "17" to Sky("大暴雨", R.mipmap.weather_icon_17, R.mipmap.bg_rain),
    "18" to Sky("特大暴雨", R.mipmap.weather_icon_18, R.mipmap.bg_rain),
    "19" to Sky("冻雨", R.mipmap.weather_icon_19, R.mipmap.bg_rain),
    "20" to Sky("雨夹雪", R.mipmap.weather_icon_20, R.mipmap.bg_rain),
    "21" to Sky("阵雪", R.mipmap.weather_icon_21, R.mipmap.bg_snow),
    "22" to Sky("小雪", R.mipmap.weather_icon_22, R.mipmap.bg_snow),
    "23" to Sky("中雪", R.mipmap.weather_icon_23, R.mipmap.bg_snow),
    "24" to Sky("大雪", R.mipmap.weather_icon_24, R.mipmap.bg_snow),
    "25" to Sky("暴雪", R.mipmap.weather_icon_25, R.mipmap.bg_snow),
    "26" to Sky("浮尘", R.mipmap.weather_icon_26, R.mipmap.bg_fog),
    "27" to Sky("扬沙", R.mipmap.weather_icon_27, R.mipmap.bg_fog),
    "28" to Sky("沙尘暴", R.mipmap.weather_icon_28, R.mipmap.bg_fog),
    "29" to Sky("强沙尘暴", R.mipmap.weather_icon_29, R.mipmap.bg_fog),
    "30" to Sky("雾", R.mipmap.weather_icon_30, R.mipmap.bg_fog),
    "31" to Sky("霾", R.mipmap.weather_icon_31, R.mipmap.bg_fog),
    "32" to Sky("风", R.mipmap.weather_icon_32, R.mipmap.bg_wind),
    "33" to Sky("大风", R.mipmap.weather_icon_33, R.mipmap.bg_wind),
    "34" to Sky("飓风", R.mipmap.weather_icon_34, R.mipmap.bg_wind),
    "35" to Sky("热带风暴", R.mipmap.weather_icon_35, R.mipmap.bg_wind),
    "36" to Sky("龙卷风", R.mipmap.weather_icon_36, R.mipmap.bg_wind),
    "37" to Sky("冷", R.mipmap.weather_icon_37, R.mipmap.bg_snow),
    "38" to Sky("热", R.mipmap.weather_icon_38, R.mipmap.bg_clear_day),
    "99" to Sky("未知", R.mipmap.weather_icon_99, R.mipmap.bg_clear_day),
)

fun getSky(code: String): Sky {
    return sky[code] ?: sky["99"]!!
}