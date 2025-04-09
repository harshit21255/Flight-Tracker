package com.example.flighttracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flighttracker.model.FlightData

@Composable
fun FlightInfoCard(flightData: FlightData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Airline: ${flightData.airline?.name ?: "N/A"}", style = MaterialTheme.typography.titleMedium)
            Text("Flight: ${flightData.flight?.iata ?: "N/A"}")
            Text("Departure: ${flightData.departure?.airport ?: "N/A"} at ${flightData.departure?.actual ?: "N/A"}")
            Text("Arrival: ${flightData.arrival?.airport ?: "N/A"} at ${flightData.arrival?.actual ?: "N/A"}")

            flightData.live?.let {
                Text("In Air: ${if (!it.is_ground) "Yes" else "No"}")
                Text("Latitude: ${it.latitude ?: "N/A"}")
                Text("Longitude: ${it.longitude ?: "N/A"}")
                Text("Altitude: ${it.altitude ?: "N/A"} m")
                Text("Speed: ${it.speed_horizontal ?: "N/A"} km/h")
                Text("Direction: ${it.direction ?: "N/A"}°")
            }
        }
    }
}
