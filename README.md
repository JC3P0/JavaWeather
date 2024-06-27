# JavaWeather

    - JavaWeather is a weather application built using JavaFX. 
    - It collects and displays current weather data and a 5-day forecast for a specified location using the OpenWeatherMap API.

## Features

    - Displays current weather conditions including temperature, humidity, wind speed, and visibility.
    - Provides a 5-day weather forecast with high and low temperatures, weather conditions, and icons.
    - Allows users to add and view weather for multiple locations.
    - Graphical user interface designed using JavaFX.

## Requirements

    - Java version: 21.0.3
    - JavaFX: javafx-sdk-21.0.3
    - JavaFX: javafx-jmods-21.0.3
    - Apache Maven 3.9.8
    - See `pom.xml` for the full list of dependencies using Maven.

## Setup

    1. **Clone the repository:**
    2. Add JavaFX and other dependencies:
    3. Create a config.properties file in the resource directory and add your OpenWeatherMap API key: api.key=your_openweathermap_api_key

## Project Structure

    src/
    |-- main/
    |   |-- java/
    |   |   |-- javaweathers/
    |   |   |   |-- AddLocationController.java: Controller for adding a new location.
    |   |   |   |-- Day.java: Represents a single day's weather data.
    |   |   |   |-- FiveDayForecastController.java: Controller for the 5-day forecast view.
    |   |   |   |-- Forecast.java: Collects and parses the 5-day weather forecast data.
    |   |   |   |-- Launch.java: Ensures JavaFX application runs properly.
    |   |   |   |-- MainApplication.java: The main entry point of the application.
    |   |   |   |-- MainController.java: Controller for the main application view.
    |   |   |   |-- Weather.java: Collects and parses the current weather data.
    |   |-- resources/
    |   |   |-- javaweathers/
    |   |   |   |-- icons/: Contains the icon files for the application.
    |   |   |   |-- AddLocationView.fxml: FXML file for the add location view.
    |   |   |   |-- FiveDayForecastView.fxml: FXML file for the 5-day forecast view.
    |   |   |   |-- main-view.fxml: FXML file for the main application view.
    |   |-- config.properties: Configuration file for storing the OpenWeatherMap API key (not included in the repository).

## Usage

    - Add a location:
        Enter the city name in the "Add Location" screen.
        Click the "Enter" button to collect and display the weather data.

    - View the forecast:
        Click on the "Forecast" button to view the 5-day weather forecast.

    - Clear the weather data:
        Click on the "Clear" button to remove the weather data for the specified location.

## License

    - This project is licensed under the MIT License.

## Acknowledgments

    - OpenWeatherMap for providing the weather data API.
    - Emoji support provided by EmojiTextFlow and EmojiTwo.
