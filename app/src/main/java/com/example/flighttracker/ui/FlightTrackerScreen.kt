package com.example.flighttracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flighttracker.viewmodel.FlightViewModel
import kotlinx.coroutines.delay

@Composable
fun FlightTrackerScreen(viewModel: FlightViewModel) {
    var flightIATA by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = flightIATA,
            onValueChange = { flightIATA = it },
            label = { Text("Enter Flight Number") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.startTracking(flightIATA) }) {
            Text("Track Flight")
        }

        Spacer(modifier = Modifier.height(16.dp))

        viewModel.flightData.value?.let { flight ->
            FlightInfoCard(flight) 
        }

        viewModel.error.value?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }
    }

    LaunchedEffect(viewModel.trackingIATA) {
        while (viewModel.trackingIATA.isNotEmpty()) {
            viewModel.fetchFlight(viewModel.trackingIATA)
            delay(60000)
        }
    }
}
