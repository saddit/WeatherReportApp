package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Station(
    val aqi: String?,
    val pm25: String?,
    val pm10: String?,
    val so2: String?,
    val no2: String?,
    val co: String?,
    val o3: String?,
    @SerializedName("primary_pollutant")
    val primaryPollutant: String,
    val station: String,
    val latitude: String?,
    val longitude: String?,
    @SerializedName("last_update")
    val lastUpdate: String?,
)

data class AirQuality(
    val aqi: String?,
    val pm25: String?,
    val pm10: String?,
    val so2: String?,
    val no2: String?,
    val co: String?,
    val o3: String?,
    @SerializedName("primary_pollutant")
    val primaryPollutant: String?,
    val quality: String?,
    @SerializedName("last_update")
    val lastUpdate: String?,
    @SerializedName("date", alternate = ["time"])
    val date: String?,
)

data class Air(
    val city: AirQuality,
    val stations: List<Station>,
)

data class AirQualityRank(
    val location: Location,
    val aqi: String,
    var no: String?=null,
)

data class MultiAirQualityResult(
    val location: Location,
    @SerializedName("daily", alternate = ["hourly"])
    val multi: List<AirQuality>,
    @SerializedName("last_update")
    val lastUpdate: String,
)

data class AirQualityResult(
    val location: Location,
    val air: Air,
)

data class AirQualityFromServer(
    val results: List<AirQualityResult>
)

data class MultiAirQualityFromServer(
    val results: List<MultiAirQualityResult>
)

data class AirQualityRankFromServer(
    val results: List<AirQualityRank>
)