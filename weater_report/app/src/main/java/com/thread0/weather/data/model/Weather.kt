/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName

/**
 *@ClassName: Location
 *@Description: 心知 Location 位置类
 *@Author: hongzf
 *@Date: 2021/5/30 4:59 下午 Created
 */
data class Location(
    val id: String,
    val name: String,
    val country: String,
    val path: String,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: String
)

/**
 *@ClassName: Weather
 *@Description: 心知 Weather 天气类
 *@Author: hongzf
 *@Date: 2021/5/30 5:00 下午 Created
 */
data class Weather(
    @SerializedName("text")
    val weather: String,
    val code: Int,
    val temperature: Int,
    @SerializedName("feels_like")
    val feelsLike: Int,
    val pressure: Int,
    val humidity: Int,
    val visibility: Double,
    @SerializedName("wind_direction")
    val windDirection: String,
    @SerializedName("wind_direction_degree")
    val windDirection_degree: Int,
    @SerializedName("wind_speed")
    val windSpeed: String,
    @SerializedName("wind_scale")
    val windScale: String,
    val clouds: String,
    @SerializedName("dew_point")
    val dewPoint: String
)

/**
 *@ClassName: WeatherResult
 *@Description: 天气结果类
 *@Author: hongzf
 *@Date: 2021/5/30 5:07 下午 Created
 */
data class WeatherResult(
    val location: Location,
    val now: Weather,
    @SerializedName("last_update")
    val lastUpdate: String
)

/**
 *@ClassName: WeatherFromServer
 *@Description: 服务端天气返回结果类
 *@Author: hongzf
 *@Date: 2021/5/30 6:46 下午 Created
 */
data class WeatherFromServer(
    val results: List<WeatherResult>
)









