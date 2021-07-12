/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.net.service

import com.thread0.weather.data.model.WeatherFromServer
import com.thread0.weather.net.WEATHER_PRIVATE_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    /**
     * TODO：数据获取示例，仿照此处即可
     *       val weatherService =
     *          ScaffoldConfig.getRepositoryManager().obtainRetrofitService(WeatherService::class.java)
     *       weatherService.getLocationCurrentWeather("xxxx")//获取返回数据
     */
    @GET("/v3/weather/now.json")
    suspend fun getLocationCurrentWeather(
        @Query("location") location: String,
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
        @Query("unit") unit: String = "c"
    ): WeatherFromServer?
}