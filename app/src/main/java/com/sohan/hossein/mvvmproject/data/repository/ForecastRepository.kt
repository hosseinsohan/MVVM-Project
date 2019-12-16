package com.sohan.hossein.mvvmproject.data.repository

import androidx.lifecycle.LiveData
import com.sohan.hossein.mvvmproject.data.db.entity.WeatherLocation
import com.sohan.hossein.mvvmproject.data.db.unitlocalized.MetricCurrentWeatherEntry
import com.sohan.hossein.mvvmproject.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}