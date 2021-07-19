package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName

data class Moon(
    val date: String,
    val rise: String,
    val set: String,
    val fraction: String,
    val phase: String,
    @SerializedName("phase_name")
    val phaseName: String,
)

data class MoonResult(
    val location: Location,
    val moon: List<Moon>
)

data class MoonResultFromServer(val results: List<MoonResult>)

data class Sun(
    val date: String,
    val sunrise: String,
    val sunset: String,
)

data class SunResult(
    val location: Location,
    val sun: List<Sun>
)

data class SunResultFromServer(val results: List<SunResult>)