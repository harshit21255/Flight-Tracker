package com.example.flighttracker.api

import com.example.flighttracker.model.FlightResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AviationStackApi {
    @GET("v1/flights")
    suspend fun getFlightByIATA(
        @Query("access_key") apiKey: String,
        @Query("flight_iata") flightIATA: String
    ): Response<FlightResponse>
}
