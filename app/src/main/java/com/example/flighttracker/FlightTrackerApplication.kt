package com.example.flighttracker

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.flighttracker.data.FlightDatabase
import com.example.flighttracker.worker.DataCollectionWorker
import com.jakewharton.threetenabp.AndroidThreeTen
import java.util.concurrent.TimeUnit

class FlightTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FlightDatabase.getDatabase(this)

        AndroidThreeTen.init(this)

        startDataCollection()
    }

    private fun startDataCollection() {
        val workManager = WorkManager.getInstance(this)

        // demo
        val immediateRequest = OneTimeWorkRequestBuilder<DataCollectionWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()


        val dailyRequest = PeriodicWorkRequestBuilder<DataCollectionWorker>(
            1, TimeUnit.DAYS
        ).build()

        workManager.enqueue(immediateRequest)

        workManager.enqueueUniquePeriodicWork(
            "DailyFlightDataRefresh",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyRequest
        )
    }
}