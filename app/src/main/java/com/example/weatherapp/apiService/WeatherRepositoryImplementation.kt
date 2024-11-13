package com.example.weatherapp.apiService

import com.example.weatherapp.model.geocoding.GeocodingItemModel
import com.example.weatherapp.model.weather.WeatherModel
import javax.inject.Inject

class WeatherRepositoryImplementation @Inject constructor(private val apiService: ApiService):WeatherRepository {
    override suspend fun fetchWeather(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherModel {
       return apiService.callWeatherApi(latitude, longitude, apiKey)
    }

    override suspend fun fetchCoordinates (
        query: String,
        limit: Int,
        apiKey: String
    ): List<GeocodingItemModel> {
     return apiService.callCoordinates(query, limit, apiKey)
    }
}