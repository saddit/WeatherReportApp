package com.thread0.weather.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "t_port_city")
data class PortCity(
    @PrimaryKey
    @SerializedName("港口ID")
    val portCode: String,

    @ColumnInfo(index = true)
    @SerializedName("城市坐标ID")
    val cityCode: String,

    @ColumnInfo(index = true)
    @SerializedName("城市")
    val cityName: String,

    @ColumnInfo
    @SerializedName("港口名")
    val portName: String,

)