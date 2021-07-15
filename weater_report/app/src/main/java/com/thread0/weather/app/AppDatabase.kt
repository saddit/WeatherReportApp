package com.thread0.weather.app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thread0.weather.data.model.City

@Database(entities = [City::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        var instance: AppDatabase? = null
        fun initialize(context: Context) {
            instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "knows_weather"
            ).allowMainThreadQueries().build()
        }
    }
}