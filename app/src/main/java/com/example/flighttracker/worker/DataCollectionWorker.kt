package com.example.flighttracker.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.flighttracker.data.FlightDatabase
import com.example.flighttracker.data.FlightHistory
import com.example.flighttracker.api.RetrofitInstance
import com.example.flighttracker.utils.Constants
import com.example.flighttracker.utils.DateUtils
import org.threeten.bp.LocalDate
import java.lang.IllegalArgumentException

class DataCollectionWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val database by lazy { FlightDatabase.getDatabase(applicationContext) }
    private val apiService = RetrofitInstance.api

    override suspend fun doWork(): Result {
        return try {
            collectFlightDataForRoutes()
            Result.success()
        } catch (e: Exception) {
            Log.e("WORKER", "Failed to collect flight data", e)
            Result.retry()
        }
    }

    private suspend fun collectFlightDataForRoutes() {
        Constants.TRACKED_ROUTES.forEach { (route, flightNumbers) ->
            val (departure, arrival) = route.split("-")
            flightNumbers.forEach { flightNumber ->
                try {
                    fetchAndStoreFlights(flightNumber, departure, arrival)
                } catch (e: Exception) {
                    Log.e("WORKER", "Failed to process $flightNumber", e)
                }
            }
        }
    }

    private suspend fun fetchAndStoreFlights(
        flightNumber: String,
        departureCode: String,
        arrivalCode: String
    ) {
        val response = apiService.getFlightByIATA(Constants.API_KEY, flightNumber)

        if (!response.isSuccessful) {
            throw IllegalStateException("API request failed: ${response.code()}")
        }

        response.body()?.data?.forEach { flightData ->
            try {
                val history = FlightHistory(
                    flightNumber = flightData.flight?.iata ?: flightNumber,
                    departureAirport = flightData.departure?.iata
                        ?: throw IllegalArgumentException("Missing departure IATA"),
                    arrivalAirport = flightData.arrival?.iata
                        ?: throw IllegalArgumentException("Missing arrival IATA"),
                    scheduledDeparture = flightData.departure?.scheduled ?: "",
                    actualDeparture = flightData.departure?.actual ?: "",
                    scheduledArrival = flightData.arrival?.scheduled ?: "",
                    actualArrival = flightData.arrival?.actual ?: "",
                    delay = flightData.departure?.delay ?: 0,
                    date = flightData.flightDate ?: LocalDate.now().toString()
                )

                val rowId = database.flightDao().insert(history)
                if (rowId == -1L) {
                    Log.d("DB", "Duplicate skipped: ${history.flightNumber} on ${history.date}")
                } else {
                    Log.i("DB", "Inserted with ID $rowId: ${history.flightNumber}")
                }
            } catch (e: Exception) {
                Log.e("PROCESS", "Failed to create history for $flightNumber", e)
            }
        }
    }

    companion object {
        const val WORKER_TAG = "FlightDataCollectionWorker"
    }
}