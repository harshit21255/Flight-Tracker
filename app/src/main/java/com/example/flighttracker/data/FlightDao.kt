package com.example.flighttracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FlightDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(flight: FlightHistory): Long

    @Query("SELECT * FROM flight_history WHERE flightNumber = :flightNumber AND date(date) >= date(:sevenDaysAgo)")
    suspend fun getFlightHistory(flightNumber: String, sevenDaysAgo: String): List<FlightHistory>

    @Query("SELECT * FROM flight_history WHERE departureAirport = :departure AND arrivalAirport = :arrival AND date(date) >= date(:sevenDaysAgo)")
    suspend fun getRouteHistory(departure: String, arrival: String, sevenDaysAgo: String): List<FlightHistory>

    @Query("DELETE FROM flight_history")
    suspend fun deleteAll()
}