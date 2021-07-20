package com.thread0.weather.data.model


data class Restriction(
    val penalty:String,
    val region:String,
    val remarks:String,
    val limits:List<Limit>
)
data class Limit(
    val date:String,
    val plates: List<String>,
    val memo: String,
)

data class RestrictionResult(
    val location: Location,
    val restriction: Restriction
)
data class RestrictionResultFromServer(
    val results: List<RestrictionResult>
)