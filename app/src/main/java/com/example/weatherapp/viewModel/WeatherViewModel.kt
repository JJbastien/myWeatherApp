package com.example.weatherapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.apiService.WeatherRepository
import com.example.weatherapp.model.geocoding.GeocodingItemModel
import com.example.weatherapp.model.weather.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO  // Make dispatcher injectable
) : ViewModel() {

    private val _weather = MutableStateFlow(WeatherModel())
    val weather: StateFlow<WeatherModel> = _weather.asStateFlow() // Use asStateFlow()

    private val _search = MutableStateFlow<List<GeocodingItemModel>>(emptyList())
    val search: StateFlow<List<GeocodingItemModel>> = _search.asStateFlow()

    fun getWeather(latitude: Double, longitude: Double, apiKey: String) {
        viewModelScope.launch(dispatcher) {
            try {
                val response = repository.fetchWeather(latitude, longitude, apiKey)
                _weather.value = response
                Log.d("response", response.toString())
            } catch (e: Exception) {
                throw Exception("Error fetching weather: ${e.message}", e)
            }
        }
    }

    fun getLocation(query: String, apiKey: String) {
        viewModelScope.launch(dispatcher) {
            try {
                val response = repository.fetchCoordinates(query, 5, apiKey)
                _search.value = response
            } catch (e: Exception) {
                throw Exception("Error fetching weather: ${e.message}", e)
            }
        }
    }
}
