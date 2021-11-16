package com.concise.weather.models

import com.google.gson.annotations.SerializedName

data class WeatherForecastForCity(
    @SerializedName("cod") val cod : Int,
    @SerializedName("message") val message : Int,
    @SerializedName("city") val city : City,
    @SerializedName("cnt") val cnt : Int,
    @SerializedName("list") val list : kotlin.collections.List<List>
)