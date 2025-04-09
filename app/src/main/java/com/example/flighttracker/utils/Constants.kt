// utils/Constants.kt
package com.example.flighttracker.utils

object Constants {
    const val API_KEY = ""
    const val TRACKED_FLIGHTS = "AI2927,6E6318,QP1719,AI2807,6E5257,QP1350,6E5388,QP1738,AI427"
    val TRACKED_ROUTES = mapOf(
        "DEL-BOM" to listOf("AI2927", "6E6318", "QP1719"),
        "DEL-BLR" to listOf("AI2807", "6E5257", "QP1350"),
        "BOM-BLR" to listOf("6E5388", "QP1738", "AI427")
    )
}