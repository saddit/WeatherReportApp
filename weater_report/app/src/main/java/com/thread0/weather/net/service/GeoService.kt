package com.thread0.weather.net.service

import com.thread0.weather.data.model.MoonResultFromServer
import com.thread0.weather.data.model.MultiWeatherFromServer
import com.thread0.weather.data.model.SunResultFromServer
import com.thread0.weather.net.WEATHER_PRIVATE_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoService  {
    /**
     * 获取日出日若
     */
    @GET("/v3/geo/sun.json")
    suspend fun getDailySunData(
        @Query("location") location: String,
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("start") start: Int = 0,
        @Query("days") days: Int = 1,
        @Query("language") language: String = "zh-Hans",
    ): SunResultFromServer?

    @GET("/v3/geo/moon.json")
    suspend fun getDailyMoonData(
        @Query("location") location: String,
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("start") start: Int = 0,
        @Query("days") days: Int = 1,
        @Query("language") language: String = "zh-Hans",
    ): MoonResultFromServer?
}