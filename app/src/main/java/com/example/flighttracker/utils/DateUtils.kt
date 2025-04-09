package com.example.flighttracker.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.flighttracker.data.FlightHistory
import java.time.LocalDate
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

public object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getSevenDaysAgoDate(): String {
        return LocalDate.now().minusDays(7).toString()
    }


    fun calculateDuration(scheduledDeparture: String, actualArrival: String): Long? {
        return try {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val depart = LocalDateTime.parse(scheduledDeparture, formatter)
            val arrive = LocalDateTime.parse(actualArrival, formatter)
            Duration.between(depart, arrive).toMinutes()
        } catch (e: Exception) {
            null
        }
    }

    fun formatMinutesToTime(minutes: Int): String {
        return when {
            minutes < 0 -> "Invalid"
            minutes == 0 -> "0m"
            else -> {
                val hours = minutes / 60
                val remainingMinutes = minutes % 60
                buildString {
                    if (hours > 0) append("${hours}h ")
                    if (remainingMinutes > 0) append("${remainingMinutes}m")
                }.trim()
            }
        }
    }

    fun logDateFormats(flight: FlightHistory) {
        try {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val scheduled = LocalDateTime.parse(flight.scheduledDeparture, formatter)
            val actual = LocalDateTime.parse(flight.actualArrival, formatter)
            Log.d("DATE_DEBUG", "Successfully parsed dates for ${flight.flightNumber}")
        } catch (e: Exception) {
            Log.e("DATE_DEBUG", "Failed to parse dates for ${flight.flightNumber}", e)
        }
    }
}