package com.thread0.weather.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "t_city")
data class City(
    @PrimaryKey
    @SerializedName("城市ID")
    val code: String,

    @ColumnInfo
    @SerializedName("行政归属")
    val ascription: String,

    @ColumnInfo
    @SerializedName("拼音")
    val phonetic: String,

    @ColumnInfo
    @SerializedName("城市简称")
    val name: String,
): Serializable
