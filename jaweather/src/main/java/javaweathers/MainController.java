package javaweathers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pavlobu.emojitextflow.EmojiTextFlow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController implements Initializable {
    // Logger for logging errors and other information
    private static final Logger logger = Logger.getLogger(MainController.class.getName());
    // Stage, scene, and root used for switching views
    private Stage stage;
    private Scene scene;
    private Parent root;
    // Keeps track of the current module (0, 1, or 2)
    private static int module = 0;
    // Array to store weather data for three different locations
    static Weather weathers0 = new Weather();
    static Weather weathers1 = new Weather();
    static Weather weathers2 = new Weather();
    // FXML UI components
    @FXML
    private Button addButton0, addButton1, addButton2, fiveDayForecastButton0, fiveDayForecastButton1, fiveDayForecastButton2, clear0, clear1, clear2;
    @FXML
    private Label addLabel0, addLabel1, addLabel2, sunriseLabel0, sunriseLabel1, sunriseLabel2, sunsetLabel0, sunsetLabel1, sunsetLabel2;
    @FXML
    private ImageView weatherIcon0, weatherIcon1, weatherIcon2;
    @FXML
    private Label name0, name1, name2, description0, description1, description2, temp0, temp1, temp2, humid0, humid1, humid2, wind0, wind1, wind2, sunRise0, sunRise1, sunRise2, sunSet0, sunSet1, sunSet2, date0, date1, date2;
    @FXML
    private EmojiTextFlow emojiTextFlow0, emojiTextFlow1, riseIcon0, riseIcon1, riseIcon2, setIcon0, setIcon1, setIcon2, humidEmoji0, humidEmoji1, humidEmoji2, windEmoji0, windEmoji1, windEmoji2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set welcome emoji text
        emojiTextFlow0.parseAndAppend(":rainbow::new_moon::crescent_moon::wind_blowing_face::ocean::zap::new_moon_with_face::snowflake::full_moon_with_face:");
        emojiTextFlow1.parseAndAppend(":sun_with_face::cloud::cloud_rain::volcano::cloud_lightning::first_quarter_moon_with_face::cloud_snow::waning_gibbous_moon::rainbow:");

        // Set emojis for sunrise, sunset, wind, and humidity
        setEmojis(riseIcon0, setIcon0, windEmoji0, humidEmoji0);
        setEmojis(riseIcon1, setIcon1, windEmoji1, humidEmoji1);
        setEmojis(riseIcon2, setIcon2, windEmoji2, humidEmoji2);

        // Initialize panels based on existing weather data
        initializeWeatherPanel(0, weathers0, weathers0.getName());
        initializeWeatherPanel(1, weathers1, weathers1.getName());
        initializeWeatherPanel(2, weathers2, weathers2.getName());
    }

    // Set emojis for sunrise, sunset, wind, and humidity
    private void setEmojis(EmojiTextFlow riseIcon, EmojiTextFlow setIcon, EmojiTextFlow windEmoji, EmojiTextFlow humidEmoji) {
        riseIcon.parseAndAppend(":sunrise:");
        setIcon.parseAndAppend(":city_sunset:");
        windEmoji.parseAndAppend(":dash:");
        humidEmoji.parseAndAppend(":droplet:");
    }

    // Helper method to initialize a weather panel with data
    private void initializeWeatherPanel(int panelIndex, Weather weather, String weatherCity) {
        if (!weather.isEmpty()) {
            initializePanel(panelIndex, weather, weatherCity);
        } else {
            clearPanel(panelIndex);
        }
    }

    // Initialize UI elements with weather data
    private void initializePanel(int panelIndex, Weather weather, String weatherCity) {
        weather = Weather.fetchWeatherForCity(weatherCity);
        switch (panelIndex) {
            case 0 -> updateUI(panelIndex, weather, addButton0, addLabel0, clear0, fiveDayForecastButton0, weatherIcon0, sunRise0, sunSet0, description0, temp0, humid0, wind0, date0, name0, riseIcon0, sunriseLabel0, setIcon0, sunsetLabel0, humidEmoji0, windEmoji0);
            case 1 -> updateUI(panelIndex, weather, addButton1, addLabel1, clear1, fiveDayForecastButton1, weatherIcon1, sunRise1, sunSet1, description1, temp1, humid1, wind1, date1, name1, riseIcon1, sunriseLabel1, setIcon1, sunsetLabel1, humidEmoji1, windEmoji1);
            case 2 -> updateUI(panelIndex, weather, addButton2, addLabel2, clear2, fiveDayForecastButton2, weatherIcon2, sunRise2, sunSet2, description2, temp2, humid2, wind2, date2, name2, riseIcon2, sunriseLabel2, setIcon2, sunsetLabel2, humidEmoji2, windEmoji2);
            default -> logger.log(Level.WARNING, "Invalid panel index:", panelIndex);
        }
    }

    // Helper method to update UI elements with weather data
    private void updateUI(int panelIndex, Weather weather, Button addButton, Label addLabel, Button clear, Button fiveDayForecastButton, ImageView weatherIcon, Label sunRise, Label sunSet, Label description, Label temp, Label humid, Label wind, Label date, Label name, EmojiTextFlow riseIcon, Label sunriseLabel, EmojiTextFlow setIcon, Label sunsetLabel, EmojiTextFlow humidEmoji, EmojiTextFlow windEmoji) {
        addButton.setVisible(false);
        temp.setText(weather.getCurrentTemp() + "˚F");
        humid.setText(weather.getHumidity() + "% Humidity");
        wind.setText("Wind " + Math.round(weather.getWind()) + " mph");
        date.setText(weather.getDate());
        name.setText(weather.getName() + ", " + weather.getCountry());
        addLabel.setVisible(false);
        clear.setVisible(true);
        sunRise.setText(weather.convertSunRiseSunSet(weather.getSunRise()));
        sunSet.setText(weather.convertSunRiseSunSet(weather.getSunSet()));
        fiveDayForecastButton.setVisible(true);
        weatherIcon.setVisible(true);
        setWeatherIcon(panelIndex, weather.getIcon());
        description.setText(weather.getDescription());
        riseIcon.setVisible(true);
        setIcon.setVisible(true);
        sunriseLabel.setVisible(true);
        sunsetLabel.setVisible(true);
        humidEmoji.setVisible(true);
        windEmoji.setVisible(true);
    }

    // Set the city for the current module and fetch the weather data
    public static void setWeatherCity(String city) {
        switch (module) {
            case 0 -> weathers0 = Weather.fetchWeatherForCity(city);
            case 1 -> weathers1 = Weather.fetchWeatherForCity(city);
            case 2 -> weathers2 = Weather.fetchWeatherForCity(city);
            default -> logger.log(Level.WARNING, "Invalid module index:", module);
        }
    }

    // Set the weather icon in the corresponding ImageView
    private void setWeatherIcon(int panel, String iconCode) {
        String iconUrl = "http://openweathermap.org/img/wn/" + iconCode + "@4x.png";
        Image tempImage = new Image(iconUrl, true);
        switch (panel) {
            case 0 -> weatherIcon0.setImage(tempImage);
            case 1 -> weatherIcon1.setImage(tempImage);
            case 2 -> weatherIcon2.setImage(tempImage);
            default -> logger.log(Level.WARNING, "Invalid panel index:", panel);
        }
    }

    // Helper method to clear the panel
    private void clearPanel(int panelIndex) {
        switch (panelIndex) {
            case 0 -> resetPanel(addButton0, addLabel0, clear0, fiveDayForecastButton0, weatherIcon0, sunRise0, sunSet0, description0, temp0, humid0, wind0, date0, name0, riseIcon0, sunriseLabel0, setIcon0, sunsetLabel0, humidEmoji0, windEmoji0);
            case 1 -> resetPanel(addButton1, addLabel1, clear1, fiveDayForecastButton1, weatherIcon1, sunRise1, sunSet1, description1, temp1, humid1, wind1, date1, name1, riseIcon1, sunriseLabel1, setIcon1, sunsetLabel1, humidEmoji1, windEmoji1);
            case 2 -> resetPanel(addButton2, addLabel2, clear2, fiveDayForecastButton2, weatherIcon2, sunRise2, sunSet2, description2, temp2, humid2, wind2, date2, name2, riseIcon2, sunriseLabel2, setIcon2, sunsetLabel2, humidEmoji2, windEmoji2);
            default -> logger.log(Level.WARNING, "Invalid panel index:", panelIndex);
        }
        switch (panelIndex) {
            case 0 -> weathers0 = new Weather();
            case 1 -> weathers1 = new Weather();
            case 2 -> weathers2 = new Weather();
        }
    }

    // Helper method to reset UI elements for a panel
    private void resetPanel(Button addButton, Label addLabel, Button clear, Button fiveDayForecastButton, ImageView weatherIcon, Label sunRise, Label sunSet, Label description, Label temp, Label humid, Label wind, Label date, Label name, EmojiTextFlow riseIcon, Label sunriseLabel, EmojiTextFlow setIcon, Label sunsetLabel, EmojiTextFlow humidEmoji, EmojiTextFlow windEmoji) {
        addButton.setVisible(true);
        addLabel.setVisible(true);
        clear.setVisible(false);
        fiveDayForecastButton.setVisible(false);
        weatherIcon.setVisible(false);
        sunRise.setText("");
        sunSet.setText("");
        description.setText("");
        temp.setText("");
        humid.setText("");
        wind.setText("");
        date.setText("");
        name.setText("");
        riseIcon.setVisible(false);
        sunriseLabel.setVisible(false);
        setIcon.setVisible(false);
        sunsetLabel.setVisible(false);
        humidEmoji.setVisible(false);
        windEmoji.setVisible(false);
    }

    // Helper method to switch views
    private void switchView(String fxml, ActionEvent event, int width, int height) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    // Clear the data for the specified panel
    @FXML
    private void onClearButtonClick(ActionEvent event) throws IOException {
        int buttonId = Integer.parseInt(((Button) event.getSource()).getId().replaceAll("\\D", ""));
        clearPanel(buttonId);
    }

    // Switch to the "Add Location" view when an add button is clicked
    @FXML
    public void switchToAddLocationView(ActionEvent event) throws IOException {
        module = Integer.parseInt(((Button) event.getSource()).getId().replaceAll("\\D", ""));
        switchView("AddLocationView.fxml", event, 425, 275);
    }

    // Switch to the "Five Day Forecast" view when a forecast button is clicked
    @FXML
    public void switchToFiveDayForecastView(ActionEvent event) throws IOException {
        int buttonId = Integer.parseInt(((Button) event.getSource()).getId().replaceAll("\\D", ""));
        // Forecast testForecast = Forecast.fetchWeatherForCity(formattedCity);
        // if ()
        switch (buttonId) {
            case 0 -> FiveDayForecastController.setForecast(weathers0);
            case 1 -> FiveDayForecastController.setForecast(weathers1);
            case 2 -> FiveDayForecastController.setForecast(weathers2);
            default -> logger.log(Level.WARNING, "Invalid button ID:", buttonId);
        }
        switchView("FiveDayForecastView.fxml", event, 600, 350);
    }
}
