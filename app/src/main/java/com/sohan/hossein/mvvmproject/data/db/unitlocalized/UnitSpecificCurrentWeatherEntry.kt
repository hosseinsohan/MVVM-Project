package com.sohan.hossein.mvvmproject.data.db.unitlocalized

interface UnitSpecificCurrentWeatherEntry {

    val temperature: Double
    val weatherDescriptions: List<String>
    val weatherIconsUrl: List<String>
    val windSpeed: Double
    val windDirection: String
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
    val precipitation: Double

}