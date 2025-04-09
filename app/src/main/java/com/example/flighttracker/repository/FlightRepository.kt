package com.example.flighttracker.repository

import com.example.flighttracker.api.AviationStackApi
import com.example.flighttracker.data.FlightDao
import com.example.flighttracker.utils.Constants
import javax.inject.Inject

class FlightRepository @Inject constructor(
    private val api: AviationStackApi,
    private val dao: FlightDao
) {
    suspend fun getFlightData(flightNumber: String) = api.getFlightByIATA(
        Constants.API_KEY,
        flightNumber
    )

    suspend fun getFlightHistory(flightNumber: String, date: String) =
        dao.getFlightHistory(flightNumber, date)

    suspend fun getRouteHistory(departure: String, arrival: String, date: String) =
        dao.getRouteHistory(departure, arrival, date)
}