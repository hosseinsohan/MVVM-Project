package com.sohan.hossein.mvvmproject.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sohan.hossein.mvvmproject.data.network.response.CurrentWeatherResponse
import com.sohan.hossein.mvvmproject.internal.NoConnectivityExeption

class WeatherNetworkDataSourceImpl(
    private val apiweatherstackApiServise: ApiweatherstackApiServise
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather
    override suspend fun fetchCurrentWeather(location: String , units: String) {
       try{
           val fetchedCurrentWeather = apiweatherstackApiServise
               .getCuttentWeather(location,units)
               .await()
           _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
       }
       catch (e: NoConnectivityExeption){
           Log.e("Connectivity" , "No internet connention" , e)
       }
    }
}