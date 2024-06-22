package javaweathers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FiveDayForecastController implements Initializable {

    private static Forecast currentForecast;
    private static String locationStr, forecastCountry;

    @FXML
    private Label high1, high2, high3, high4, high5;
    @FXML
    private Label low1, low2, low3, low4, low5;
    @FXML
    private Label weather1, weather2, weather3, weather4, weather5;
    @FXML
    private Label date1, date2, date3, date4, date5;
    @FXML
    private ImageView weatherForecast1, weatherForecast2, weatherForecast3, weatherForecast4, weatherForecast5;
    @FXML
    private Label lowAverage, highAverage;
    @FXML
    private Label location;

    // Sets the forecast data and location details
    public static void setForecast(Weather weatherObj, String weatherCity) {
        currentForecast = Forecast.fetchForecastForLocation(weatherObj.getLat(), weatherObj.getLon());
        locationStr = weatherCity;
        forecastCountry = weatherObj.getCountry();
    }

    // Sets the label text with the formatted temperature
    private void setLabel(Label label, double value) {
        label.setText(Math.round(value) + " °F");
    }

    // Sets the weather icon for the ImageView
    private void setWeatherIcon(ImageView imageView, String iconCode) {
        String iconUrl = "http://openweathermap.org/img/wn/" + iconCode + "@2x.png";
        Image tempImage = new Image(iconUrl, true);
        imageView.setImage(tempImage);
    }

    // Initializes the controller class and updates the UI
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateUI();
    }

    // Updates the UI with the forecast data
    private void updateUI() {
        List<Day> days = currentForecast.getDays();

        // Check if there is sufficient data for a 5-day forecast
        if (days.size() < 5) {
            System.err.println("Insufficient data for 5-day forecast");
            return;
        }

        // Calculate average high and low temperatures
        double averageHigh = calculateAverageTemp(days, true);
        double averageLow = calculateAverageTemp(days, false);

        // Set average temperatures and location details
        setLabel(highAverage, averageHigh);
        setLabel(lowAverage, averageLow);
        location.setText(locationStr + ", " + forecastCountry);

        // Arrays for UI elements
        Label[] highs = {high1, high2, high3, high4, high5};
        Label[] lows = {low1, low2, low3, low4, low5};
        Label[] weathers = {weather1, weather2, weather3, weather4, weather5};
        Label[] dates = {date1, date2, date3, date4, date5};
        ImageView[] weatherIcons = {weatherForecast1, weatherForecast2, weatherForecast3, weatherForecast4, weatherForecast5};

        // Update the UI elements with the forecast data
        for (int i = 0; i < 5; i++) {
            Day day = days.get(i);
            setLabel(highs[i], day.getHighTemp());
            setLabel(lows[i], day.getLowTemp());
            weathers[i].setText(day.getDescription());
            dates[i].setText(currentForecast.convertTime(day.getTimestamp(), currentForecast.getTimeZone()));
            setWeatherIcon(weatherIcons[i], day.getIcon());
        }
    }

    // Calculates the average temperature (high or low) from the list of days
    private double calculateAverageTemp(List<Day> days, boolean isHigh) {
        double sum = 0;
        for (Day day : days) {
            sum += isHigh ? day.getHighTemp() : day.getLowTemp();
        }
        return sum / days.size();
    }

    // Switches to the main view
    @FXML
    public void switchToMainView(ActionEvent event) throws IOException {
        switchView(event, "main-view.fxml", 900, 600);
    }

    // Helper method to switch views
    private void switchView(ActionEvent event, String fxml, int width, int height) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }
}
