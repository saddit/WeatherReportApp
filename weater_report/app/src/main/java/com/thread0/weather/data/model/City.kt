package com.thread0.weather.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
)