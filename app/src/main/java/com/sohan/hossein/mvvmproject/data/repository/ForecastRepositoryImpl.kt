package com.sohan.hossein.mvvmproject.data.repository

import android.app.DownloadManager
import androidx.lifecycle.LiveData
import com.sohan.hossein.mvvmproject.data.db.CurrentWeatherDao
import com.sohan.hossein.mvvmproject.data.db.WeatherLocationDao
import com.sohan.hossein.mvvmproject.data.db.entity.IMPERIAL_CURRENT_WEATHER_ID
import com.sohan.hossein.mvvmproject.data.db.entity.METRIC_CURRENT_WEATHER_ID
import com.sohan.hossein.mvvmproject.data.db.entity.WeatherLocation
import com.sohan.hossein.mvvmproject.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.sohan.hossein.mvvmproject.data.network.WeatherNetworkDataSource
import com.sohan.hossein.mvvmproject.data.network.response.CurrentWeatherResponse
import com.sohan.hossein.mvvmproject.data.provider.LocationProvider
import com.sohan.hossein.mvvmproject.data.provider.UnitProvider
import com.sohan.hossein.mvvmproject.internal.UnitSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime


class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        //download data from server into persistFetchedWeather function
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    //get currentWeather from database
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData(metric)
            return@withContext if(metric)currentWeatherDao.getCurrentWeather() else currentWeatherDao.getImperialCurrentWeather()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    //insert currentWeather to datatabase
    private fun persistFetchedCurrentWeather(fetchWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            if(fetchWeather.request.unit.equals("m")){
                fetchWeather.currentWeatherEntry.id = METRIC_CURRENT_WEATHER_ID
                currentWeatherDao.upsert(fetchWeather.currentWeatherEntry)
                weatherLocationDao.upsert(fetchWeather.location)
            }
            else if(fetchWeather.request.unit.equals("f")){
                fetchWeather.currentWeatherEntry.id = IMPERIAL_CURRENT_WEATHER_ID
                currentWeatherDao.upsert(fetchWeather.currentWeatherEntry)
                weatherLocationDao.upsert(fetchWeather.location)
           }
        }
    }

    private suspend fun initWeatherData(metric: Boolean){
        val lasteWeatherLocation = weatherLocationDao.getLocation().value

        if(lasteWeatherLocation == null || locationProvider.hasLocationChanged(lasteWeatherLocation)){
            fethCurrentWeather(metric)
            return
        }


        if(isFetchCurrentNeeded(lasteWeatherLocation.zonedDateTime))
            fethCurrentWeather(metric)
    }


    private suspend fun fethCurrentWeather(isMetric: Boolean){

        var unit: String
        if(isMetric)unit = "m" else unit="f"

        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocation(),
            unit
        )
    }
    //check 30 min (for update database)
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thiryMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thiryMinutesAgo)
    }
}