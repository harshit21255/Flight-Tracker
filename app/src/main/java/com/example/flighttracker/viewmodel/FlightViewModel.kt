package com.example.flighttracker.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flighttracker.api.RetrofitInstance
import com.example.flighttracker.model.FlightData
import kotlinx.coroutines.launch

class FlightViewModel : ViewModel() {
    val error = mutableStateOf<String?>(null)
    var flightData = mutableStateOf<FlightData?>(null)
        private set
    var trackingIATA = ""

    fun startTracking(iata: String) {
        if (iata.isBlank()) {
            error.value = "Please enter a valid flight number."
            return
        }
        trackingIATA = iata
        fetchFlight(iata)
    }

    fun fetchFlight(iata: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getFlightByIATA(
                    "c3a8bff9bdd8ce54acf3bff81bbeafb2", iata
                )
                if (response.isSuccessful) {
                    flightData.value = response.body()?.data?.firstOrNull()
                    error.value = if (flightData.value == null) "No flight data found." else null

                } else {
                    error.value = "API Error: ${response.code()}"
                }
            } catch (e: Exception) {
                error.value = "Network error: ${e.message}"
            }
        }
    }
}
