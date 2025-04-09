package com.example.flighttracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flighttracker.model.FlightData

@Composable
fun FlightInfoCard(flightData: FlightData, averageTime: String?, averageDelay: String?) {
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
                Text("In Air: ${if (!it.isGround) "Yes" else "No"}")
                Text("Latitude: ${it.latitude ?: "N/A"}")
                Text("Longitude: ${it.longitude ?: "N/A"}")
                Text("Altitude: ${it.altitude ?: "N/A"} m")
                Text("Speed: ${it.speedHorizontal ?: "N/A"} km/h")
                Text("Direction: ${it.direction ?: "N/A"}°")
            }

            averageTime?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Average Route Time: $it",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary)
            }

            averageDelay?.let {
                Text("Avg departure delay: $it")
            }
        }
    }
}