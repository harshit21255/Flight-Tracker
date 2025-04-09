package com.example.flighttracker.model

import com.google.gson.annotations.SerializedName

data class FlightResponse(
    val data: List<FlightData>?
)

data class FlightData(
    @SerializedName("flight_date") val flightDate: String?,
    @SerializedName("flight_status") val flightStatus: String?,
    val departure: DepartureInfo?,
    val arrival: ArrivalInfo?,
    val airline: Airline?,
    val flight: FlightInfo?,
    val live: LiveData?
)

data class DepartureInfo(
    val airport: String?,
    val iata: String?,
    val delay: Int?,
    val scheduled: String?,
    val actual: String?
)

data class ArrivalInfo(
    val airport: String?,
    val iata: String?,
    val delay: Int?,
    val scheduled: String?,
    val actual: String?
)

data class Airline(
    val name: String?
)

data class FlightInfo(
    val iata: String?
)

data class LiveData(
    val latitude: Double?,
    val longitude: Double?,
    val altitude: Double?,
    val direction: Double?,
    @SerializedName("speed_horizontal") val speedHorizontal: Double?,
    @SerializedName("is_ground") val isGround: Boolean,
)