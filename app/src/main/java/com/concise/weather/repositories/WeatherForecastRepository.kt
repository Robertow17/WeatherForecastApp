package com.concise.weather.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.concise.weather.models.WeatherForecastForCity
import com.concise.weather.net.RetrofitApi
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class WeatherForecastRepository private constructor(private val context: Context) {
    private var weatherForecastForCity: WeatherForecastForCity? = null
    val weatherForecast: MutableLiveData<WeatherForecastForCity?>
        get() {
            val data = MutableLiveData<WeatherForecastForCity?>()
            getWeatherForecastForCity(data)
            return data
        }

    private fun getWeatherForecastForCity(data: MutableLiveData<WeatherForecastForCity?>) {
        val call = RetrofitApi.myApi.weatherForecastForCity
        call!!.enqueue(object : Callback<WeatherForecastForCity?> {
            override fun onResponse(call: Call<WeatherForecastForCity?>, response: Response<WeatherForecastForCity?>) {
                weatherForecastForCity = response.body()
                data.postValue(weatherForecastForCity)
            }

            override fun onFailure(call: Call<WeatherForecastForCity?>, t: Throwable) {
                val test = jSONFromResources
                val gson = Gson()
                weatherForecastForCity = gson.fromJson(test, WeatherForecastForCity::class.java)
                data.postValue(weatherForecastForCity)
            }
        })
    }

    companion object {
        private var instance: WeatherForecastRepository? = null
        @JvmStatic
        fun getInstance(context: Context): WeatherForecastRepository? {
            if (instance == null) {
                instance = WeatherForecastRepository(context)
            }
            return instance
        }
    }

    private val jSONFromResources: String
        get() {
            val ins = context.resources.openRawResource(context.resources.getIdentifier("daily",
                "raw", context.packageName))
            return readTextFile(ins)
        }

    private fun readTextFile(inputStream: InputStream): String {
        val outputStream = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var len: Int
        try {
            while (inputStream.read(buf).also { len = it } != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {}
        return outputStream.toString()
    }
}