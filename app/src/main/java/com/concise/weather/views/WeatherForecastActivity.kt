package com.concise.weather.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.concise.weather.adapters.WeekDayWeatherAdapter
import com.concise.weather.databinding.ActivityMainBinding
import com.concise.weather.models.WeatherForecastForCity
import com.concise.weather.viewmodels.WeatherForecastActivityViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.ArrayList

class WeatherForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var weekDayWeatherAdapter: WeekDayWeatherAdapter
    private lateinit var weatherForecastActivityViewModel: WeatherForecastActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        weatherForecastActivityViewModel = ViewModelProvider(this)[WeatherForecastActivityViewModel::class.java]
        weatherForecastActivityViewModel.init(this)
        weatherForecastActivityViewModel.weatherForecast!!.observe(this, Observer { weatherForecastForCity ->
            if (weatherForecastForCity != null) {
                weekDayWeatherAdapter.setWeekDayWeather(weatherForecastForCity.list)
                weekDayWeatherAdapter.setCityName(weatherForecastForCity.city.name)
                binding.cityNameTv.text = weatherForecastForCity.city.name
            }
            val avgTemp = weatherForecastForCity?.let { getStringAvgTemperature(it) }
            binding.avgTempTV.text = avgTemp
            binding.avgTempLabel.visibility = View.VISIBLE
            hideProgressBar()
        })
        initRecyclerView()
    }

    private fun getStringAvgTemperature(weatherForecastForCity: WeatherForecastForCity): String {
        var tempSum = 0.0
        for (l in weatherForecastForCity.list) {
            tempSum += l.temp.day
        }
        val df = DecimalFormat("0.00")
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(kelvinToCelsius(tempSum / weatherForecastForCity.list.size)) + "°C"
    }

    private fun initRecyclerView() {
        showProgressBar()
        weekDayWeatherAdapter = WeekDayWeatherAdapter(ArrayList(), this)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.dayWeekWeatherRV.layoutManager = mLayoutManager
        binding.dayWeekWeatherRV.itemAnimator = DefaultItemAnimator()
        binding.dayWeekWeatherRV.adapter = weekDayWeatherAdapter
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }
}