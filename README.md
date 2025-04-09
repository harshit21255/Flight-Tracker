# Flight Tracker App

A Kotlin Android application that tracks flight statuses using the AviationStack API, displays real-time flight data, and calculates average flight times for selected routes.

## Features

- Real-time flight status tracking
- Live flight position data (latitude, longitude, altitude)
- Daily background data collection for selected routes
- Average flight time calculations
- Duplicate entry prevention
- Modern Jetpack Compose UI

## File Structure
```
com/example/flighttracker/
├── api/
│ ├── AviationStackApi.kt # Retrofit API interface
│ └── RetrofitInstance.kt # Retrofit client setup
│
├── data/
│ ├── FlightHistory.kt # Room database entity
│ ├── FlightDao.kt # Database access operations
│ └── FlightDatabase.kt # Room database setup
│
├── worker/
│ └── DataCollectionWorker.kt # Background data collection
│
├── viewmodel/
│ └── FlightViewModel.kt # Business logic and state management
│
├── ui/
│ ├── FlightTrackerScreen.kt # Main composable screen
│ ├── FlightInfoCard.kt # Flight data display component
│ └── theme/ # App theming
│ └── Theme.kt
│
├── utils/
│ ├── Constants.kt # API keys and configuration
│ └── DateUtils.kt # Date/time utilities
│
└── FlightTrackerApplication.kt # App entry point
```



## Setup Instructions

1. **API Key**:
   - Get an API key from [AviationStack](https://aviationstack.com/)
   - Add it to `utils/Constants.kt`:
     ```kotlin
     const val API_KEY = "your_api_key_here"
     ```

2. **Tracked Routes**:
   - Modify routes in `utils/Constants.kt`:
     ```kotlin
     val TRACKED_ROUTES = mapOf(
         "DEL-BOM" to listOf("AI2927", "6E6318", "QP1719"),
         "DEL-BLR" to listOf("AI2807", "6E5257", "QP1350"),
         "BOM-BLR" to listOf("6E5388", "QP1738", "AI427")
     )
     ```

### Usage

1. **Track a Flight**
   - Enter flight number (e.g., "AI2927")
   - View real-time status and position

2. **Background Data**
   - The app automatically collects data daily
   - Calculates average flight times for tracked routes


### Database Inspection
To view collected data:

1. Use Android Studio's Database Inspector

2. Query the flight_history table

3. Check for duplicates with:
```sql
SELECT flightNumber, date, COUNT(*) 
FROM flight_history 
GROUP BY flightNumber, date 
HAVING COUNT(*) > 1
```

