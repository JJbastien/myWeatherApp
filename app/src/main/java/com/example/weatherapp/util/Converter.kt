package com.example.weatherapp.util

import android.content.Context
import android.content.SharedPreferences

fun celciusConverter(kelvin: Double?): Int {
    if (kelvin != null) {
        return (kelvin - 273.15).toInt()
    }
    return 0
}

fun saveLastLocation(context: Context, lat: Double, lon: Double) {
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("WeatherPrefs", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPrefs.edit()
    editor.putString("lastLocationLat", lat.toString())
    editor.putString("lastLocationLon", lon.toString())
    editor.apply()
}

// Function to retrieve the last location used from SharedPreferences
fun getLastLocation(context: Context): Pair<Double, Double>? {
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("WeatherPrefs", Context.MODE_PRIVATE)
    val lat = sharedPrefs.getString("lastLocationLat", null)?.toDoubleOrNull()
    val lon = sharedPrefs.getString("lastLocationLon", null)?.toDoubleOrNull()
    return if (lat != null && lon != null) {
        Pair(lat, lon)
    } else {
        null
    }
}