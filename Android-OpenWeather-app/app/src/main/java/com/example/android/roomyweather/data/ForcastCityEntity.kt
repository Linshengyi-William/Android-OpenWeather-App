package com.example.android.roomyweather.data

import java.io.Serializable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ForcastCityEntity (
    @PrimaryKey
    var name: String,
    val timeStamp: Long

) : Serializable