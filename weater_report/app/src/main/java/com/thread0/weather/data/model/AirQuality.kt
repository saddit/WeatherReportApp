package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Station(
    val aqi: Int,
    val pm25: Int,
    val pm10: Int,
    val so2: Int,
    val no2: Int,
    val co: Double,
    val o3: Int,
    @SerializedName("primary_pollutant")
    val primaryPollutant: String,
    val station: String,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("last_update")
    val lastUpdate: LocalDateTime
)

data class AirQuality(
    val aqi: Int,
    val pm25: Int,
    val so2: Int,
    val no2: Int,
    val co: Double,
    val o3: Int,
    @SerializedName("primary_pollutant")
    val primaryPollutant: String,
    val quality: String,
    @SerializedName("last_update")
    val lastUpdate: LocalDateTime,
    val date: String,
)

data class Air(
    val city: AirQuality,
    val stations: List<Station>,
)

data class AirQualityRank(
    val location: Location,
    val aqi: Int
)

data class MultiAirQualityResult(
    val location: Location,
    @SerializedName("daily", alternate = ["hourly"])
    val multi: List<AirQuality>,
    @SerializedName("last_update")
    val lastUpdate: LocalDateTime,
)

data class AirQualityResult(
    val location: Location,
    val air: Air,
)

data class AirQualityFromServer(
    val result: List<AirQualityResult>
)

data class MultiAirQualityFromServer(
    val result: List<MultiAirQualityResult>
)

data class AirQualityRankFromServer(
    val result: List<AirQualityRank>
)