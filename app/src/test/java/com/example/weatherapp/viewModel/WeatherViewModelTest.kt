package com.example.weatherapp.viewModel

import app.cash.turbine.test
import com.example.weatherapp.apiService.WeatherRepository
import com.example.weatherapp.model.geocoding.GeocodingItemModel
import com.example.weatherapp.model.weather.WeatherModel
import com.example.weatherapp.model.weather.WeatherModelX
import com.example.weatherapp.model.weather.WindModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private val repository: WeatherRepository = mockk()
    private val testDispatcher = Dispatchers.IO

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        // Pass testDispatcher directly now
        viewModel = WeatherViewModel(repository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getLocation - when successful should update search state`() = runTest {
        // Arrange
        val query = "London"
        val apiKey = "test_api_key"
        val expectedLocations = listOf(GeocodingItemModel())

        coEvery {
            repository.fetchCoordinates(query, 5, apiKey)
        } returns expectedLocations

        val searchStates = mutableListOf<List<GeocodingItemModel>>()

        val job = launch(testDispatcher) {
            viewModel.search.collect {
                println("Collected locations: $it") // Debug print
                searchStates.add(it)
            }
        }

        // Let the collector start
        delay(100)

        // Act
        viewModel.getLocation(query, apiKey)

        // Give time for collection
        delay(100)

        // Assert
        assert(searchStates.size == 2) {
            "Expected 2 states but got ${searchStates.size}. States: $searchStates"
        }
        assert(searchStates[0].isEmpty()) {
            "First state should be empty. Got: ${searchStates[0]}"
        }
        assert(searchStates[1] == expectedLocations) {
            "Second state should be expected locations. Got: ${searchStates[1]}"
        }

        coVerify {
            repository.fetchCoordinates(query, 5, apiKey)
        }

        job.cancel()
    }
}

