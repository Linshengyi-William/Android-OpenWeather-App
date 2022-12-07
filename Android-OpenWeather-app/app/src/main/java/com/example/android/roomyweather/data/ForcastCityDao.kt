package com.example.android.roomyweather.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ForcastCityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: ForcastCityEntity)


    @Delete
    suspend fun delete(city: ForcastCityEntity)

    @Query("SELECT * FROM ForcastCityEntity ORDER BY timeStamp DESC")
    fun getAllCities(): Flow<List<ForcastCityEntity>>

    @Query("SELECT * FROM ForcastCityEntity WHERE name =:name LIMIT 1 ")
    fun getCityByName(name: String): Flow<ForcastCityEntity?>

}