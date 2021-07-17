package com.thread0.weather.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thread0.weather.data.model.City

@Dao
interface CityDao {
    @Insert(entity = City::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City)

    @Query("select * from t_city limit :num")
    suspend fun queryLimit(num: Int): List<City>

    @Query("select * from t_city where name like :keyword")
    suspend fun queryByKeyword(keyword: String): List<City>

    @Query("select * from t_city where code = :code")
    suspend fun queryByCode(code: String): City?

    @Query("select * from t_city where phonetic = :phonetic limit 1")
    suspend fun queryByPhonetic(phonetic: String): City?
}