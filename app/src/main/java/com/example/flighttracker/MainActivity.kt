package com.example.flighttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flighttracker.ui.FlightTrackerScreen
import com.example.flighttracker.viewmodel.FlightViewModel
import com.example.flighttracker.ui.theme.FlightTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlightTrackerTheme {
                val viewModel: FlightViewModel = viewModel()
                FlightTrackerScreen(viewModel)
            }
        }
    }
}
