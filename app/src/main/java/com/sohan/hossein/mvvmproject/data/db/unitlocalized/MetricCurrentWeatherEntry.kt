package com.sohan.hossein.mvvmproject.data.db.unitlocalized

import androidx.room.ColumnInfo

data class MetricCurrentWeatherEntry (
    @ColumnInfo(name = "temperature")
    override val temperature: Double,
    @ColumnInfo(name = "weatherDescriptions")
    override val weatherDescriptions: List<String>,
    @ColumnInfo(name = "weatherIcons")
    override val weatherIconsUrl: List<String>,
    @ColumnInfo(name = "windSpeed")
    override val windSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "feelslike")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "visibility")
    override val visibilityDistance: Double,
    @ColumnInfo(name = "precip")
    override val precipitation: Double


    ):UnitSpecificCurrentWeatherEntry