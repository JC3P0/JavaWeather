package javaweathers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddWeatherApiController {

    private static final Logger logger = Logger.getLogger(AddWeatherApiController.class.getName());
    private static final String ERROR_INVALID_INPUT = "(ERROR: Letters and numbers only)";
    private static final String ERROR_INVALID_API = "(ERROR: Enter a valid API)";

    @FXML
    private Label apiKeyPrompt;
    @FXML
    private TextField apiKeyField;

    @FXML
    private void onSaveButtonClick(ActionEvent event) throws IOException {
        String apiKey = apiKeyField.getText().trim();

        if (apiKey.matches("^[a-zA-Z0-9]+$")) {
            String requestURL = "http://api.openweathermap.org/data/2.5/weather?q=Tokyo&APPID=" + apiKey;
            HttpResponse<String> response = invokeGET(requestURL);

            if (response != null && response.body() != null && response.statusCode() == 200) {
                saveApiKeyToFile(apiKey);
                switchView(event, "main-view.fxml", 900, 600);
            } else {
                logger.log(Level.SEVERE, "ERROR saving API: enter a valid API!");
                apiKeyPrompt.setText(ERROR_INVALID_API);
            }
        } else {
            logger.log(Level.SEVERE, "ERROR saving API: only numbers and letters!");
            apiKeyPrompt.setText(ERROR_INVALID_INPUT);
        }
    }

    // Invoke a GET request to the given URL
    static HttpResponse<String> invokeGET(String requestURL) {
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestURL)).GET().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "ERROR: AddWeatherApiController: HttpResponse", e);
        }
        return response;
    }

    private void saveApiKeyToFile(String apiKey) {
        Properties prop = new Properties();
        prop.setProperty("apiKey", apiKey);
        try {
            ConfigManager.saveConfig(prop);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "ERROR saving API key to file", e);
        }
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
