package com.example.flighttracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flighttracker.ui.FlightTrackerScreen
import com.example.flighttracker.viewmodel.FlightViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: FlightViewModel = viewModel()
            FlightTrackerScreen(viewModel = viewModel)
        }
    }

}