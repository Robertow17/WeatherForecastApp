package com.concise.weather.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    private fun retrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val myApi: Api by lazy {
        retrofit().create(Api::class.java)
    }
}