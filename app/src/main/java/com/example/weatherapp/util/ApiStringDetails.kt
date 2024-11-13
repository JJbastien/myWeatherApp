package com.example.weatherapp.util

import com.example.weatherapp.BuildConfig
object ApiStringDetails {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val WEATHER = "data/2.5/weather"
        const val GEOCODING = "geo/1.0/direct"
        const val APIKEY = BuildConfig.API_KEY

}