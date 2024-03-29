package com.sohan.hossein.mvvmproject.ui.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sohan.hossein.mvvmproject.R

class FutureListWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            FutureListWeatherFragment()
    }

    private lateinit var weatherViewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        weatherViewModel = ViewModelProviders.of(this).get(FutureListWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
