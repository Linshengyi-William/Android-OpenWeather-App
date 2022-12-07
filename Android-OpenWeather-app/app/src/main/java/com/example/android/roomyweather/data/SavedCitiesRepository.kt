package com.example.android.roomyweather.data

class SavedCitiesRepository(
    private val dao: ForcastCityDao

    ){
    suspend fun insertSavedCity(city: ForcastCityEntity) = dao.insert(city)
    suspend fun removeSavedCity(city: ForcastCityEntity) = dao.delete(city)

    fun getAllSavedCities() = dao.getAllCities()
    fun getSavedCityByName(name: String) = dao.getCityByName(name)
}