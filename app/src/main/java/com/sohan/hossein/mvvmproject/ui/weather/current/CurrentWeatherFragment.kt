package com.sohan.hossein.mvvmproject.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.sohan.hossein.mvvmproject.R
import com.sohan.hossein.mvvmproject.data.network.ApiweatherstackApiServise
import com.sohan.hossein.mvvmproject.data.network.ConnectivityInterceptorImpl
import com.sohan.hossein.mvvmproject.internal.glide.GlideApp
import com.sohan.hossein.mvvmproject.ui.base.ScopFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*

import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopFragment() , KodeinAware{
    override val kodein by closestKodein()
    private val viewModelFactory:CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this , viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel



        updateDateToToday()
        bindUI()

    }

    private fun bindUI() = launch{
        val  currentWeather = viewModel.weather.await()

        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@CurrentWeatherFragment, Observer {
            if(it == null) return@Observer
            updateLocation(it.name)
        })

        currentWeather.observe(this@CurrentWeatherFragment , Observer {
            if(it == null) return@Observer

            group_loading.visibility=View.GONE
            updateTemperatures(it.temperature,it.feelsLikeTemperature)
            updateWeatherDescriptions(it.weatherDescriptions[0])
            updatePrecipitation(it.precipitation)
            updateWind(it.windDirection, it.windSpeed )
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
                .load(it.weatherIconsUrl[0])
                .into(imageView_condition_icon)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String , imperial: String):String{
        return if(viewModel.isMetric)metric else imperial
    }

    private fun updateLocation(location: String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "today"
    }

    private fun updateTemperatures(temperature: Double, feelslike: Double){
    val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "$feelslike $unitAbbreviation"
    }

    private fun updateWeatherDescriptions(weatherDescriptions: String){
        textView_Descriptions.text = weatherDescriptions
    }

    private fun updatePrecipitation(precipitationVolume: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation "
    }

    private fun updateWind(windDirection: String , windSpeed: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }
}
