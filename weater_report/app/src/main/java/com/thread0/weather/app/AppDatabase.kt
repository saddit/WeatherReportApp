package com.thread0.weather.app

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thread0.weather.data.dao.CityDao
import com.thread0.weather.data.dao.PortCityDao
import com.thread0.weather.data.dao.PortDao
import com.thread0.weather.data.model.City
import com.thread0.weather.data.model.Port
import com.thread0.weather.data.model.PortCity
import org.jetbrains.annotations.NotNull
import java.lang.IllegalStateException

@Database(entities = [City::class, Port::class, PortCity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCityDao(): CityDao
    abstract fun getPortDao(): PortDao
    abstract fun getPortCityDao(): PortCityDao
    companion object {
        var instance: AppDatabase? = null
            private set
            @NotNull
            get() {
                if(field == null) {
                    throw IllegalStateException(this::class.java.name + " not initialize yet! Run initialize(context) first!")
                }
                return field!!
            }

        @Synchronized
        fun initialize(context: Context) {
            instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "knows_weather"
            ).allowMainThreadQueries().build()
        }
    }
}