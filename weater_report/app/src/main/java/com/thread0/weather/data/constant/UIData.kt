package com.thread0.weather.data.constant

import android.graphics.Color
import com.thread0.weather.R

private val aqi = mapOf(
    "优" to Color.parseColor("#0BA62C"),
    "良" to Color.parseColor("#FFCC00"),
    "轻度污染" to Color.parseColor("#FF3B30"),
)

fun getAqiColor(quality: String?): Int {
    if (quality == null) return aqi["轻度污染"]!!
    return aqi[quality] ?: aqi["轻度污染"]!!
}

fun getAqiLevel(aqi: String): String {
    val aqiIntVal = Integer.parseInt(aqi);
    return when {
        aqiIntVal > 100 -> "差"
        aqiIntVal > 50 -> "良"
        else -> "优"
    }
}

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
data class AlarmKey(
    val type: String,
    val level: String,
)

private val alarm = mapOf(
    AlarmKey("default", "蓝色") to R.mipmap.ico_alarm_default_1,
    AlarmKey("default", "黄色") to R.mipmap.ico_alarm_default_2,
    AlarmKey("default", "橙色") to R.mipmap.ico_alarm_default_3,
    AlarmKey("default", "红色") to R.mipmap.ico_alarm_default_4,
    AlarmKey("大雾","蓝色") to R.mipmap.ico_alarm_heavyfog_1,
    AlarmKey("大雾","黄色") to R.mipmap.ico_alarm_heavyfog_2,
    AlarmKey("大雾","橙色") to R.mipmap.ico_alarm_heavyfog_3,
    AlarmKey("大雾","红色") to R.mipmap.ico_alarm_heavyfog_4,
    AlarmKey("干旱","蓝色") to R.mipmap.ico_alarm_drought_1,
    AlarmKey("干旱","黄色") to R.mipmap.ico_alarm_drought_2,
    AlarmKey("干旱","橙色") to R.mipmap.ico_alarm_drought_3,
    AlarmKey("干旱","红色") to R.mipmap.ico_alarm_drought_4,
    AlarmKey("霜冻","蓝色") to R.mipmap.ico_alarm_forst_1,
    AlarmKey("霜冻","黄色") to R.mipmap.ico_alarm_forst_2,
    AlarmKey("霜冻","橙色") to R.mipmap.ico_alarm_forst_3,
    AlarmKey("霜冻","红色") to R.mipmap.ico_alarm_forst_4,
    AlarmKey("大风","蓝色") to R.mipmap.ico_alarm_gale_1,
    AlarmKey("大风","黄色") to R.mipmap.ico_alarm_gale_2,
    AlarmKey("大风","橙色") to R.mipmap.ico_alarm_gale_3,
    AlarmKey("大风","红色") to R.mipmap.ico_alarm_gale_4,
    AlarmKey("冰雹","蓝色") to R.mipmap.ico_alarm_hail_1,
    AlarmKey("冰雹","黄色") to R.mipmap.ico_alarm_hail_2,
    AlarmKey("冰雹","橙色") to R.mipmap.ico_alarm_hail_3,
    AlarmKey("冰雹","红色") to R.mipmap.ico_alarm_hail_4,
    AlarmKey("雾霾","蓝色") to R.mipmap.ico_alarm_haze_1,
    AlarmKey("雾霾","黄色") to R.mipmap.ico_alarm_haze_2,
    AlarmKey("雾霾","橙色") to R.mipmap.ico_alarm_haze_3,
    AlarmKey("雾霾","红色") to R.mipmap.ico_alarm_haze_4,
    AlarmKey("高温","蓝色") to R.mipmap.ico_alarm_heatwave_1,
    AlarmKey("高温","黄色") to R.mipmap.ico_alarm_heatwave_2,
    AlarmKey("高温","橙色") to R.mipmap.ico_alarm_heatwave_3,
    AlarmKey("高温","红色") to R.mipmap.ico_alarm_heatwave_4,
    AlarmKey("雷电","蓝色") to R.mipmap.ico_alarm_lightning_1,
    AlarmKey("雷电","黄色") to R.mipmap.ico_alarm_lightning_2,
    AlarmKey("雷电","橙色") to R.mipmap.ico_alarm_lightning_3,
    AlarmKey("雷电","红色") to R.mipmap.ico_alarm_lightning_4,
    AlarmKey("暴雨","蓝色") to R.mipmap.ico_alarm_rainstorm_1,
    AlarmKey("暴雨","黄色") to R.mipmap.ico_alarm_rainstorm_2,
    AlarmKey("暴雨","橙色") to R.mipmap.ico_alarm_rainstorm_3,
    AlarmKey("暴雨","红色") to R.mipmap.ico_alarm_rainstorm_4,
    AlarmKey("道路结冰","蓝色") to R.mipmap.ico_alarm_roadicing_1,
    AlarmKey("道路结冰","黄色") to R.mipmap.ico_alarm_roadicing_2,
    AlarmKey("道路结冰","橙色") to R.mipmap.ico_alarm_roadicing_3,
    AlarmKey("道路结冰","红色") to R.mipmap.ico_alarm_roadicing_4,
    AlarmKey("沙暴","蓝色") to R.mipmap.ico_alarm_sandstorm_1,
    AlarmKey("沙暴","黄色") to R.mipmap.ico_alarm_sandstorm_2,
    AlarmKey("沙暴","橙色") to R.mipmap.ico_alarm_sandstorm_3,
    AlarmKey("沙暴","红色") to R.mipmap.ico_alarm_sandstorm_4,
    AlarmKey("暴雪","蓝色") to R.mipmap.ico_alarm_snowstorm_1,
    AlarmKey("暴雪","黄色") to R.mipmap.ico_alarm_snowstorm_2,
    AlarmKey("暴雪","橙色") to R.mipmap.ico_alarm_snowstorm_3,
    AlarmKey("暴雪","红色") to R.mipmap.ico_alarm_snowstorm_4,
    AlarmKey("台风","蓝色") to R.mipmap.ico_alarm_typhoon_1,
    AlarmKey("台风","黄色") to R.mipmap.ico_alarm_typhoon_2,
    AlarmKey("台风","橙色") to R.mipmap.ico_alarm_typhoon_3,
    AlarmKey("台风","红色") to R.mipmap.ico_alarm_typhoon_4,
    AlarmKey("台风","白色") to R.mipmap.ico_alarm_typhoon_5,
    AlarmKey("森林火险","蓝色") to R.mipmap.ico_alarm_wildfire_1,
    AlarmKey("森林火险","黄色") to R.mipmap.ico_alarm_wildfire_2,
    AlarmKey("森林火险","橙色") to R.mipmap.ico_alarm_wildfire_3,
    AlarmKey("森林火险","红色") to R.mipmap.ico_alarm_wildfire_4,
)

fun getAlarm(type:String, level: String):Int? {
    return alarm[AlarmKey(type,level)] ?: alarm[AlarmKey("default", level)]
}