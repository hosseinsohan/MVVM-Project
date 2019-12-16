package com.sohan.hossein.mvvmproject.ui.weather.current

import androidx.lifecycle.ViewModel
import com.sohan.hossein.mvvmproject.data.provider.UnitProvider
import com.sohan.hossein.mvvmproject.data.repository.ForecastRepository
import com.sohan.hossein.mvvmproject.internal.UnitSystem
import com.sohan.hossein.mvvmproject.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem=unitProvider.getUnitSystem()

    val isMetric: Boolean
    get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}
