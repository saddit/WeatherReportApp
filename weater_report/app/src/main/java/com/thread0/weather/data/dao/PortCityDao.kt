package com.thread0.weather.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thread0.weather.data.model.PortCity

@Dao
interface PortCityDao {
    @Insert(entity = PortCity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(portCity: PortCity)

    @Query("select count(0) > 0 from t_port_city where cityCode = :cityCode")
    suspend fun isPortCity(cityCode: String): Boolean
}