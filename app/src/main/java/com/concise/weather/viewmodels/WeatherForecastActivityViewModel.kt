package com.concise.weather.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.concise.weather.models.WeatherForecastForCity
import com.concise.weather.repositories.WeatherForecastRepository
import com.concise.weather.repositories.WeatherForecastRepository.Companion.getInstance

class WeatherForecastActivityViewModel : ViewModel() {
    private var mWeatherForecast: MutableLiveData<WeatherForecastForCity?>? = null
    private var mRepo: WeatherForecastRepository? = null

    fun init(context: Context?) {
        if (mWeatherForecast != null) return
        mRepo = getInstance(context!!)
        mWeatherForecast = mRepo!!.weatherForecast
    }

    val weatherForecast: LiveData<WeatherForecastForCity?>?
        get() = mWeatherForecast
}