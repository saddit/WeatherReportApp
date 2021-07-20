package com.thread0.weather.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thread0.weather.data.model.Port

@Dao
interface PortDao {
    @Insert(entity = Port::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(port: Port)

    @Query("select count(0) from t_port")
    suspend fun countTotal():Int

    @Query("select * from t_port p,t_port_city pc where p.code = pc.portCode and pc.cityCode = :cityCode")
    suspend fun queryByCityCode(cityCode: String?): List<Port>
}