package com.concise.weather.models

import com.google.gson.annotations.SerializedName

data class List (
    @SerializedName("dt") val dt : Long,
    @SerializedName("temp") val temp : Temp,
    @SerializedName("pressure") val pressure : Double,
    @SerializedName("humidity") val humidity : Int,
    @SerializedName("weather") val weather : kotlin.collections.List<Weather>,
    @SerializedName("speed") val speed : Double,
    @SerializedName("deg") val deg : Int,
    @SerializedName("clouds") val clouds : Int,
    @SerializedName("snow") val snow : Double
)