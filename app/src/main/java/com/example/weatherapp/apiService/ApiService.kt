package com.example.weatherapp.apiService

import com.example.weatherapp.util.ApiStringDetails
import com.example.weatherapp.model.geocoding.GeocodingItemModel
import com.example.weatherapp.model.weather.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiStringDetails.WEATHER)
    suspend fun callWeatherApi(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): WeatherModel

    @GET(ApiStringDetails.GEOCODING)
    suspend fun callCoordinates(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): List<GeocodingItemModel>
}