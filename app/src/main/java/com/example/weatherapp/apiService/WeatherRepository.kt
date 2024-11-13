package com.example.weatherapp.apiService

import com.example.weatherapp.model.geocoding.GeocodingItemModel
import com.example.weatherapp.model.weather.WeatherModel

interface WeatherRepository {

    suspend fun fetchWeather(latitude: Double, longitude: Double, apiKey: String): WeatherModel

    suspend fun fetchCoordinates(query: String, limit: Int, apiKey: String): List<GeocodingItemModel>
}