package com.sohan.hossein.mvvmproject.data.provider

import com.sohan.hossein.mvvmproject.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocation(): String
}