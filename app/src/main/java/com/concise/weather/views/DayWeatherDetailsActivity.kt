package com.concise.weather.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.concise.weather.databinding.ActivityDayWeatherDetailsBinding
import com.concise.weather.databinding.ActivityMainBinding

class DayWeatherDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDayWeatherDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayWeatherDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setValuesToViews(intent)
    }

    private fun setValuesToViews(intent: Intent) {
        with (intent) {
            binding.currentDateTV.text = getStringExtra("Date")
            binding.currentTemp.text = getStringExtra("DayTemp")
            binding.cityName.text = getStringExtra("City")
            binding.weatherDesc.text = getStringExtra("Description")
            binding.currentTempMin.text = getStringExtra("MinTemp")
            binding.currentTempMax.text = getStringExtra("MaxTemp")
            binding.currentPressure.text = getStringExtra("Pressure")
            binding.currentWindSpeed.text = intent.getStringExtra("Wind")
        }
        val weatherImageURL = "https://openweathermap.org/img/w/" + intent.getStringExtra("Icon") + ".png"
        Glide.with(this).load(weatherImageURL).into(binding.weatherImage)
    }
}