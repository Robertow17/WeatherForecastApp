package com.concise.weather.net

import com.concise.weather.models.WeatherForecastForCity
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    companion object {
        const val BASE_URL = "https://api.npoint.io/"
    }

    @get:GET("f439d825f6169b7dc013")
    val weatherForecastForCity: Call<WeatherForecastForCity?>?


}