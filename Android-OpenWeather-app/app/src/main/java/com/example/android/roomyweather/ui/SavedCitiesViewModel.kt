package com.example.android.roomyweather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.roomyweather.data.AppDatabase
import com.example.android.roomyweather.data.ForcastCityEntity
import com.example.android.roomyweather.data.SavedCitiesRepository
import kotlinx.coroutines.launch

class SavedCitiesViewModel(application: Application) : AndroidViewModel(application) {
    private val city = SavedCitiesRepository(
        AppDatabase.getInstance(application).forecastCityDao()
    )

    val savedCities = city.getAllSavedCities().asLiveData()

    fun addSavedCity(cityInFunc: ForcastCityEntity){
        viewModelScope.launch {
            city.insertSavedCity(cityInFunc)
        }
    }
    fun removeSavedCity(cityInFunc: ForcastCityEntity){
        viewModelScope.launch {
            city.removeSavedCity(cityInFunc)
        }
    }
    fun getSavedCityByName (name: String) =
        city.getSavedCityByName(name).asLiveData()

}

