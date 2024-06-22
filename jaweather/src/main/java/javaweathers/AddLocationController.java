package javaweathers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddLocationController {

    private static final Logger logger = Logger.getLogger(AddLocationController.class.getName());
    private static final String ERROR_INVALID_INPUT = "(ERROR: Letters and spaces only)";
    private static final String ERROR_INVALID_CITY = "ERROR: Enter a valid city";
    private static final int MIN_CITY_LENGTH = 2;
    private static final int MAX_CITY_LENGTH = 50;

    @FXML
    private Label cityPrompt; // Label to display prompts or errors
    @FXML
    private TextField cityField; // TextField for user to enter city name

    // Method to switch back to the main view
    @FXML
    public void switchToMainView(ActionEvent event) throws IOException {
        // Get and trim the city name entered by the user
        String city = cityField.getText().trim();

        // Check if the input contains only letters and spaces
        if (!city.matches("[a-zA-Z ]+") || city.length() < MIN_CITY_LENGTH || city.length() > MAX_CITY_LENGTH) {
            cityPrompt.setText(ERROR_INVALID_INPUT);
            return;
        }

        // Format the city name for the API request
        String formattedCity = city.replace(" ", "+");
        
        // Fetch weather data for the specified city
        Weather currentWeather = Weather.fetchWeatherForCity(formattedCity);

        // If weather data is successfully retrieved, switch to the main view
        if (!currentWeather.isEmpty()) {
            MainController.setWeatherCity(formattedCity);
            Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();
        } else {
            // Display an error message if the weather data could not be retrieved
            logger.log(Level.SEVERE, "ERROR creating weather object for city:", formattedCity);
            cityPrompt.setText(ERROR_INVALID_CITY);
        }
    }

    public TextField getCityField() {
        return cityField;
    }
    
    public Label getCityPrompt() {
        return cityPrompt;
    }
    
}
