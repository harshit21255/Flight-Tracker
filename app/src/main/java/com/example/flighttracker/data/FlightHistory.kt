package com.example.flighttracker.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "flight_history",
    indices = [
        Index(value = ["flightNumber", "date"], unique = true)
    ]
)
data class FlightHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val flightNumber: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val scheduledDeparture: String,
    val actualDeparture: String,
    val scheduledArrival: String,
    val actualArrival: String,
    val delay: Int,
    val date: String
)