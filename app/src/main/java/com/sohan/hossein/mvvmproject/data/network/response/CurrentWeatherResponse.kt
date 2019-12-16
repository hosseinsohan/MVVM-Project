package com.sohan.hossein.mvvmproject.data.network.response


import com.google.gson.annotations.SerializedName
import com.sohan.hossein.mvvmproject.data.db.entity.CurrentWeatherEntry
import com.sohan.hossein.mvvmproject.data.db.entity.WeatherLocation
import com.sohan.hossein.mvvmproject.data.db.entity.Request

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation,
    val request: Request
)