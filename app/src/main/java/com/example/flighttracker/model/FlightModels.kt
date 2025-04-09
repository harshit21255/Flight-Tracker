package com.example.flighttracker.model

data class FlightResponse(val data: List<FlightData>?)

data class FlightData(
    val flight: FlightInfo?,
    val airline: Airline?,
    val departure: AirportInfo?,
    val arrival: AirportInfo?,
    val live: LiveData?,
    val flightStatus: String?
)

data class FlightInfo(val iata: String?)
data class Airline(val name: String?)
data class AirportInfo(
    val airport: String?,
    val actual: String?,
    val scheduled: String?
)
data class LiveData(
    val is_ground: Boolean,
    val latitude: Double?,
    val longitude: Double?,
    val altitude: Double?,
    val speed_horizontal: Double?,
    val direction: Double?
)
