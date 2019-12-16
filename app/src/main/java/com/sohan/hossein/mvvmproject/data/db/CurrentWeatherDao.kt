package com.sohan.hossein.mvvmproject.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sohan.hossein.mvvmproject.data.db.entity.CurrentWeatherEntry
import com.sohan.hossein.mvvmproject.data.db.entity.IMPERIAL_CURRENT_WEATHER_ID
import com.sohan.hossein.mvvmproject.data.db.entity.METRIC_CURRENT_WEATHER_ID
import com.sohan.hossein.mvvmproject.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.sohan.hossein.mvvmproject.data.db.unitlocalized.MetricCurrentWeatherEntry
import com.sohan.hossein.mvvmproject.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from cuttent_weather where id = $METRIC_CURRENT_WEATHER_ID")
    fun getCurrentWeather(): LiveData<MetricCurrentWeatherEntry>

    @Query("select * from cuttent_weather where id = $IMPERIAL_CURRENT_WEATHER_ID")
    fun getImperialCurrentWeather(): LiveData<MetricCurrentWeatherEntry>

   // @Query("select * from current_weather where id = $IMPERIAL_CURRENT_WEATHER_ID")
   // fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>

}