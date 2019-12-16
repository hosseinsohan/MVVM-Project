package com.sohan.hossein.mvvmproject.data.network

import androidx.lifecycle.LiveData
import com.sohan.hossein.mvvmproject.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(location: String , units: String)
}