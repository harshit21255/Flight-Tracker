package com.example.flighttracker.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.flighttracker.viewmodel.FlightViewModel
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FlightTrackerScreen(viewModel: FlightViewModel) {
    var flightIATA by remember { mutableStateOf("") }
    val flightData by viewModel.flightData.collectAsState()
    val error by viewModel.error.collectAsState()
    val averageTime by viewModel.averageTime.collectAsState()
    val averageDelay by viewModel.averageDelay.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = flightIATA,
            onValueChange = { flightIATA = it },
            label = { Text("Enter Flight Number") },
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = { viewModel.startTracking(flightIATA) }) {
            Text("Track Flight")
        }

        Spacer(Modifier.height(16.dp))

//        Button(
//            onClick = { viewModel.loadTestData(flightIATA) },
//            modifier = Modifier.padding(8.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Red.copy(alpha = 0.3f)
//            )
//        ) {
//            Text("Load Test Data (DEBUG)")
//        }

//        Button(
//            onClick = { viewModel.calculateAllRouteAverages() },
//            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
//        ) {
//            Text("Calculate All Route Averages")
//        }

//        viewModel.routeAverages.value.forEach { (route, averages) ->
//            val (time, delay) = averages
//            RouteAverageCard(
//                route = route,
//                averageTime = time,
//                averageDelay = delay
//            )
//        }
        
        Spacer(Modifier.height(16.dp))

        flightData?.let { flight ->
            FlightInfoCard(flightData = flight, averageTime = averageTime, averageDelay = averageDelay)
        }

        error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }
    }

    LaunchedEffect(viewModel.trackingIATA) {
        while (viewModel.trackingIATA.isNotEmpty()) {
            viewModel.fetchFlight()
            delay(60000)
        }
    }
}

//@Composable
//fun RouteAverageCard(route: String, averageTime: String, averageDelay: String) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text("Route: $route", style = MaterialTheme.typography.titleMedium)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text("Average Time: $averageTime")
//            Text("Average Delay: $averageDelay")
//        }
//    }
//}