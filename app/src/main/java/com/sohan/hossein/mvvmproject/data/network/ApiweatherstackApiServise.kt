package com.sohan.hossein.mvvmproject.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sohan.hossein.mvvmproject.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY="016711e65bfa881feac17b8ae8fc4816"

/*
http://api.weatherstack.com/current?access_key=016711e65bfa881feac17b8ae8fc4816&query=New%20York
? access_key = YOUR_ACCESS_KEY
& query = New York

// optional parameters:

& units = m
& language = en
& callback = MY_CALLBACK
 */

interface ApiweatherstackApiServise {

    @GET("current")
    fun getCuttentWeather(
        @Query("query") location : String,
        @Query("units") units : String
        ):Deferred<CurrentWeatherResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiweatherstackApiServise {

            val requestInterceptor = Interceptor{ chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key",
                        API_KEY
                    )
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiweatherstackApiServise::class.java)
        }
    }
}