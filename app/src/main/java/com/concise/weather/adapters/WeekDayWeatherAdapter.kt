package com.concise.weather.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.concise.weather.R
import com.concise.weather.databinding.WeekDayWeatherRowBinding
import com.concise.weather.views.DayWeatherDetailsActivity
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class WeekDayWeatherAdapter(private var weekDayWeather: List<com.concise.weather.models.List>, private val context: Context) : RecyclerView.Adapter<WeekDayWeatherAdapter.WeekDayWeatherViewHolder>() {
    private var cityName = ""

    inner class WeekDayWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = WeekDayWeatherRowBinding.bind(view)
    }

    fun setCityName(cityName: String) {
        this.cityName = cityName
    }

    fun setWeekDayWeather(weekDayWeather: List<com.concise.weather.models.List>) {
        this.weekDayWeather = weekDayWeather
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekDayWeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.week_day_weather_row, parent, false)

        val holder = WeekDayWeatherViewHolder(itemView)

        itemView.setOnClickListener {
            val pos = holder.absoluteAdapterPosition
            if(pos!= RecyclerView.NO_POSITION) {
                val myIntent = Intent(context, DayWeatherDetailsActivity::class.java)
                val dayWeather = weekDayWeather[pos]
                with(dayWeather){
                    val date = timeInMillisToDate(dt)
                    val df = DecimalFormat("0.00")
                    df.roundingMode = RoundingMode.HALF_UP
                    val dayTemp = df.format(temp.day.let { kelvinToCelsius(it) }) + "°C"
                    val minTemp = df.format(temp.min.let { kelvinToCelsius(it) }) + "°C"
                    val maxTemp = df.format(temp.max.let { kelvinToCelsius(it) }) + "°C"
                    myIntent.putExtra("Date", date)
                    myIntent.putExtra("DayTemp", dayTemp)
                    myIntent.putExtra("MinTemp", minTemp)
                    myIntent.putExtra("MaxTemp", maxTemp)
                    myIntent.putExtra("Pressure", "$pressure hPa")
                    myIntent.putExtra("Wind", "$speed m/s")
                    myIntent.putExtra("Icon", weather[0].icon)
                    myIntent.putExtra("Description", weather[0].description)
                    myIntent.putExtra("City", cityName)
                }
                context.startActivity(myIntent)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: WeekDayWeatherViewHolder, position: Int) {
        val dayWeather = weekDayWeather[position]
        with (dayWeather) {
            val date = timeInMillisToDate(dt)
            val df = DecimalFormat("0.00")
            df.roundingMode = java.math.RoundingMode.HALF_UP
            val dayTemp = df.format(kelvinToCelsius(temp.day)) + "°C"
            val minTemp = df.format(kelvinToCelsius(temp.min)) + "°C"
            val maxTemp = df.format(kelvinToCelsius(temp.max)) + "°C"

            holder.binding.dateTV.text = date
            holder.binding.dayTempTV.text = dayTemp
            holder.binding.minTempTV.text = minTemp
            holder.binding.maxTempTV.text = maxTemp
        }
    }

    override fun getItemCount(): Int {
        return weekDayWeather.size
    }

    private fun timeInMillisToDate(time: Long): String {
        val pattern = "EEE, dd-mm-yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(Date(time))
    }

    private fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }
}