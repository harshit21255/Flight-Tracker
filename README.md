# FlightTracker - Flight Journey Tracker

FlightTracker is an Android application that allows users to track flights in real time. By inputting a flight number (IATA code), users can see detailed information about the flight's status, including the airline, departure and arrival airports, live location, and more. The app dynamically updates the flight information every minute to provide the latest updates.

## Features

### Flight Tracking:
- Allows users to input a flight number (IATA code) and track the status of the flight.
- Displays details such as the airline, departure and arrival airports, flight status, and live data (location, speed, altitude).
- Updates the flight information every minute, keeping the user informed in real time.

### Real-Time Updates:
- Automatically fetches flight data every minute to keep the user updated.
- Displays live location data, including latitude, longitude, altitude, and speed.

### Error Handling:
- Displays clear error messages if no flight data is found, if the API call fails, or if there is a network issue.
- Provides feedback for incorrect flight numbers and helps the user retry.

### User Interface:
- A clean and simple UI using Jetpack Compose to input the flight number and view flight details.
- Displays flight information in a visually appealing format, using Material 3 components.

### Continuous Tracking:
- Once the flight number is entered and tracking is started, the app automatically fetches the latest flight data every minute.

## Project Structure

### Kotlin Files

- **FlightViewModel.kt**: 
  - Handles the logic for fetching flight data using the AviationStack API.
  - Contains functions for tracking flights and managing errors.

- **AviationStackApi.kt**: 
  - Defines the API interface for making requests to the AviationStack API to fetch flight details based on the IATA code.

- **RetrofitInstance.kt**: 
  - Sets up the Retrofit instance to communicate with the AviationStack API.

- **FlightModels.kt**: 
  - Defines the data models that represent the flight data, including `FlightResponse`, `FlightData`, `FlightInfo`, `Airline`, `AirportInfo`, and `LiveData`.

- **FlightTrackerScreen.kt**: 
  - The main UI component that allows users to input the flight number and track the flight.
  - Displays the flight details dynamically as the data is fetched.

- **FlightInfoCard.kt**: 
  - A composable function that renders the flight information in a card format.

### Layout Files
- **MainActivity.kt**: 
  - The main activity that hosts the FlightTrackerScreen UI and manages interactions.
  - Initializes the `FlightViewModel` and passes it to the composables.

### Resource Files

- **res/values/colors.xml**: 
  - Defines the color scheme for the app, including primary and accent colors.

- **res/values/styles.xml**: 
  - Contains custom styles for the app, such as Material 3 button and text styling.

- **res/values/strings.xml**: 
  - Contains string resources for UI elements like labels and error messages.

- **res/drawable/custom_progress_bar.xml**: 
  - A custom drawable for any loading or progress indicators, if added in the future.

## Setup Instructions

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/yourusername/FlightTracker.git
   ```

2. **Open the Project in Android Studio**:
   - Open Android Studio and select "Open an Existing Project."
   - Navigate to the cloned repository and select the `FlightTracker` folder.

3. **Build and Run the App**:
   - Connect an Android device or start an emulator.
   - Click on the "Run" button in Android Studio to build and run the app.

## Usage

### Start the App:
- The app will load the main screen where you can enter a flight number (IATA code) to start tracking the flight.

### Track a Flight:
- Enter the flight number in the text field and click the "Track Flight" button.
- The app will fetch flight details from the AviationStack API and display the airline, flight number, departure and arrival airports, and live data (latitude, longitude, altitude, speed).

### Real-Time Updates:
- The app will automatically update the flight details every minute to keep you informed with the latest flight information.

### Error Handling:
- If no flight data is found or if there is an issue with the network or API, the app will display an error message.

## Dependencies
- **Jetpack Compose**: For the UI components.
- **Retrofit**: For making network requests to the AviationStack API.
- **Coroutines**: For background tasks to fetch flight data asynchronously.
- **Material3**: For UI components and styling.

## Future Enhancements
- Add a visual map to track the flight's location in real-time.
- Improve error handling and add retry functionality.
- Allow users to save their tracked flights for future reference.

---

**FlightTracker** is built with Kotlin and Jetpack Compose, and uses the AviationStack API to fetch flight data. This app allows users to track flights in real-time and stay updated on their journey status. Happy flying! ✈️
