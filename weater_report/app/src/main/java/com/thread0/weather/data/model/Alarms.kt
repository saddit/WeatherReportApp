package com.thread0.weather.data.model

data class Alarms(
    val title: String,
    val type:String,
    val level:String,
    val status:String,
    val description:String,
    val pub_date:String
)

data class AlarmResult(
    val location: Location,
    val alarms: List<Alarms>
)

data class AlarmFromResult(
    val results: List<AlarmResult>
)