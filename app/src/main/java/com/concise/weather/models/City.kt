package com.concise.weather.models

import com.google.gson.annotations.SerializedName

data class City (
    @SerializedName("geoname_id") val geoname_id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lon") val lon : Double,
    @SerializedName("country") val country : String,
    @SerializedName("iso2") val iso2 : String,
    @SerializedName("type") val type : String,
    @SerializedName("population") val population : Int
)