package com.thread0.weather.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "t_port")
data class Port(
    @PrimaryKey
    @SerializedName("经纬度坐标ID")
    val code: String,

    @ColumnInfo
    @SerializedName("省")
    val province: String,

    @ColumnInfo
    @SerializedName("港口名")
    val name: String,

    @ColumnInfo
    @SerializedName("水位基准")
    val waterLevel: String,

    @ColumnInfo
    @SerializedName("港口名拼音")
    val phonetic: String,

    @ColumnInfo
    @SerializedName("经度")
    val longitude: String,

    @ColumnInfo
    @SerializedName("纬度")
    val latitude: String,

)