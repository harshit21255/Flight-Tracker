// viewmodel/FlightViewModel.kt
package com.example.flighttracker.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.flighttracker.model.FlightData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.AndroidViewModel
import com.example.flighttracker.api.RetrofitInstance
import com.example.flighttracker.data.FlightDatabase
import com.example.flighttracker.data.FlightHistory
import com.example.flighttracker.utils.Constants
import com.example.flighttracker.utils.DateUtils
import kotlinx.coroutines.flow.asStateFlow

class FlightViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = FlightDatabase.getDatabase(application).flightDao()

    private val _flightData = MutableStateFlow<FlightData?>(null)
    val flightData: StateFlow<FlightData?> = _flightData.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _averageTime = MutableStateFlow<String?>(null)
    val averageTime: StateFlow<String?> = _averageTime.asStateFlow()

    private val _averageDelay = MutableStateFlow<String?>(null)
    val averageDelay: StateFlow<String?> = _averageDelay.asStateFlow()

    var trackingIATA = ""

    private val database = FlightDatabase.getDatabase(application)

    val routeAverages = mutableStateOf<Map<String, Pair<String, String>>>(emptyMap())

    // New function to calculate all routes
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun calculateAllRouteAverages() {
//        viewModelScope.launch {
//            val averages = mutableMapOf<String, Pair<String, String>>()
//
//            Constants.TRACKED_ROUTES.keys.forEach { route ->
//                val (departure, arrival) = route.split("-")
//                val sevenDaysAgo = DateUtils.getSevenDaysAgoDate()
//
//                Log.d("AVG_DEBUG", "Checking route $route")
//                Log.d("AVG_DEBUG", "7 days ago date: $sevenDaysAgo")
//
//                val flights = database.flightDao().getRouteHistory(departure, arrival, sevenDaysAgo)
//                Log.d("AVG_DEBUG", "Found ${flights.size} flights for $route")
//
//                flights.forEach { flight ->
//                    Log.d("AVG_DEBUG_FLIGHT", """
//                    Flight: ${flight.flightNumber}
//                    Scheduled: ${flight.scheduledDeparture}
//                    Actual Arrival: ${flight.actualArrival}
//                    Delay: ${flight.delay}
//                """.trimIndent())
//                }
//
//                // Calculate time taken (scheduled departure to actual arrival)
//                val timeTakenList = flights.mapNotNull { flight ->
//                    if (flight.scheduledDeparture.isBlank() || flight.actualArrival.isBlank()) {
//                        Log.d("CALC_DEBUG", "Missing timestamps in ${flight.flightNumber}")
//                        null
//                    } else {
//                        DateUtils.calculateDuration(
//                            flight.scheduledDeparture,
//                            flight.actualArrival
//                        )?.also {
//                            Log.d("CALC_DEBUG", "Time taken for ${flight.flightNumber}: $it minutes")
//                        }
//                    }
//                }
//
//                val avgTime = if (timeTakenList.isNotEmpty()) {
//                    val avgMinutes = timeTakenList.average().toInt()
//                    "${avgMinutes/60}h ${avgMinutes%60}m"
//                } else "N/A"
//
//                // Calculate average delay
//                val delays = flights.map { it.delay.toDouble() }
//                val avgDelay = if (delays.isNotEmpty()) {
//                    "${delays.average().toInt()} min"
//                } else "N/A"
//
//                averages[route] = Pair(avgTime, avgDelay)
//            }
//
//            routeAverages.value = averages
//        }
//    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun startTracking(iata: String) {
        if (iata.isBlank()) {
            _error.value = "Please enter a valid flight number."
            return
        }
        trackingIATA = iata
        fetchFlight()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchFlight() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getFlightByIATA(
                    Constants.API_KEY,
                    trackingIATA
                )
                if (response.isSuccessful) {
                    _flightData.value = response.body()?.data?.firstOrNull()
                    _error.value = if (_flightData.value == null) "No flight data found." else null
                    checkForAverageTime()
                } else {
                    _error.value = "API Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkForAverageTime() {
        if (Constants.TRACKED_FLIGHTS.contains(trackingIATA)) {
            viewModelScope.launch {
                calculateAverageTime()
            }
        } else {
            _averageTime.value = null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun calculateAverageTime() {
        val sevenDaysAgo = DateUtils.getSevenDaysAgoDate()

        val (departure, arrival) = when (trackingIATA) {
            in listOf("AI2927", "6E6318", "QP1719") -> "DEL" to "BOM"
            in listOf("AI2807", "6E5257", "QP1350") -> "DEL" to "BLR"
            else -> "BOM" to "BLR"
        }

        val routeFlights = dao.getRouteHistory(departure, arrival, sevenDaysAgo)

        val validFlights = routeFlights.filter {
            !it.scheduledDeparture.isNullOrEmpty() &&
                    !it.actualArrival.isNullOrEmpty()
        }

        validFlights.forEach { flight ->
            Log.d("ASHU", """ASHU:\n
            Flight: ${flight.flightNumber}
            Scheduled: ${flight.scheduledDeparture}
            Actual Arrival: ${flight.actualArrival}
            Delay: ${flight.delay}
        """.trimIndent())
        }

        val timeTakenList = validFlights.mapNotNull { flight ->
            DateUtils.calculateDuration(
                flight.scheduledDeparture,
                flight.actualArrival
            )?.toDouble()
        }

        val delays = validFlights.mapNotNull { it.delay?.toDouble() }
        _averageTime.value = when {
            timeTakenList.isEmpty() -> "No historical data"
            else -> {
                val avgMinutes = timeTakenList.average().toInt()
                "${avgMinutes/60}h ${avgMinutes%60}m"
            }
        }

        _averageDelay.value = when {
            delays.isEmpty() -> "No delay data"
            else -> "${delays.average().toInt()} minutes"
        }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun loadTestData(iata: String) {
//        trackingIATA = iata
//        viewModelScope.launch {
//
//            val testFlights = listOf(
//                FlightHistory(
//                    flightNumber = "AI2927",
//                    departureAirport = "DEL",
//                    arrivalAirport = "BOM",
//                    scheduledDeparture = "2024-04-04T09:30:00Z",
//                    actualDeparture = "2024-04-04T10:00:00Z",
//                    scheduledArrival = "2024-04-04T12:00:00Z",
//                    actualArrival = "2024-04-04T12:00:00Z",
//                    delay = 30,
//                    date = "2024-04-04"
//                ),
//                FlightHistory(
//                    flightNumber = "6E6318",
//                    departureAirport = "DEL",
//                    arrivalAirport = "BOM",
//                    scheduledDeparture = "2024-04-05T09:30:00Z",
//                    actualDeparture = "2024-04-05T09:45:00Z",
//                    scheduledArrival = "2024-04-05T11:45:00Z",
//                    actualArrival = "2024-04-05T11:45:00Z",
//                    delay = 15,
//                    date = "2024-04-05"
//                ),
//                FlightHistory(
//                    flightNumber = "QP1719",
//                    departureAirport = "DEL",
//                    arrivalAirport = "BOM",
//                    scheduledDeparture = "2024-04-06T09:30:00Z",
//                    actualDeparture = "2024-04-06T09:30:00Z",
//                    scheduledArrival = "2024-04-06T11:30:00Z",
//                    actualArrival = "2024-04-06T11:30:00Z",
//                    delay = 0,
//                    date = "2024-04-06"
//                )
//            )
//
//            testFlights.forEach { database.flightDao().insert(it) }
//
//
//            calculateAverageTime()
//        }
//    }


}