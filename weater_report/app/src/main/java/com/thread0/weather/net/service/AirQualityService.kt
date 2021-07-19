package com.thread0.weather.net.service

import com.thread0.weather.data.model.*
import retrofit2.http.GET
import com.thread0.weather.net.WEATHER_PRIVATE_KEY
import retrofit2.http.Query

interface AirQualityService {
    /**
     * 获取当天空气质量报告
     */
    @GET("/v3/air/now.json")
    suspend fun getCurrentAirQuality(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("location") location: String?,
        @Query("scope") scope: String = "city",
        @Query("language") language: String = "zh-Hans",
    ): AirQualityFromServer?

    /**
     * 获取城市空气质量排行榜
     */
    @GET("/v3/air/ranking.json")
    suspend fun getAirQualityRank(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
    ): AirQualityRankFromServer?

    /**
     * 获取最多5天内的空气质量预报
     */
    @GET("/v3/air/daily.json")
    suspend fun getDailyAirQuality(
        @Query("location") location: String?,
        @Query("days") days: Int = 5,
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
    ): MultiAirQualityFromServer?

    /**
     * 获取最多5天内<每小时的空气质量预报
     */
    @GET("/v3/air/hourly.json")
    suspend fun getHourlyAirQuality(
        @Query("location") location: String?,
        @Query("days") days: Int = 1,
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
    ): MultiAirQualityFromServer?
}