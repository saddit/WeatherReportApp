package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName

data class Calendar (
    val date: String,
    val zodiac: String,
    @SerializedName("ganzhi_year")
    val ganzhiYear: String,
    @SerializedName("ganzhi_month")
    val ganzhiMonth: String,
    @SerializedName("ganzhi_day")
    val ganzhiDay: String,
    @SerializedName("lunar_year")
    val lunarYear: String,
    @SerializedName("lunar_month")
    val lunarMonth: String,
    @SerializedName("lunar_day")
    val lunarDay: String,
    @SerializedName("lunar_month_name")
    val lunarMonthName: String,
    @SerializedName("lunar_day_name")
    val lunarDayName: String,
    @SerializedName("lunar_leap_month")
    val lunarLeapMonth: String,
    @SerializedName("lunar_festival")
    val lunarFestival: String,
    @SerializedName("solar_term")
    val solarTerm: String,
    //以下为视图参数
    var ganzhi: String? = null,
    var lunar: String? = null,
    var lunarName: String? = null,
)

data class CalendarResult(
    @SerializedName("chinese_calendar")
    val chineseCalendar: List<Calendar>
)

data class CalendarResultFromServer(
    val results: CalendarResult
)