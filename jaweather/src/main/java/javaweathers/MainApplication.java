package javaweathers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication extends Application {
    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    @Override
    public void start(Stage stage) {
        try {
            ConfigManager.ensureConfigDirectoryExists();
            if (isApiKeyPresent()) {
                Weather testApi = Weather.fetchWeatherForCity("Tokyo");
                if (!testApi.isEmpty()) {
                    switchScene(stage, "/javaweathers/main-view.fxml", "JavaWeather", 900, 600);
                } else {
                    switchScene(stage, "/javaweathers/AddWeatherApiView.fxml", "JavaWeather", 425, 275);
                    logger.log(Level.SEVERE, "ERROR loading api: enter a valid api!");
                }
            } else {
                switchScene(stage, "/javaweathers/AddWeatherApiView.fxml", "JavaWeather", 425, 275);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error starting application", e);
        }
    }

    private boolean isApiKeyPresent() {
        try {
            Properties config = ConfigManager.loadConfig();
            String apiKey = config.getProperty("apiKey");
            return apiKey != null && !apiKey.trim().isEmpty();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not load config.properties", e);
            return false;
        }
    }

    private void switchScene(Stage stage, String fxmlPath, String title, int width, int height) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        setAppIcon(stage);
        stage.setResizable(false);
        stage.show();
    }

    private void setAppIcon(Stage stage) {
        Image icon = new Image(getClass().getResourceAsStream("/javaweathers/icons/app_icon.png"));
        if (!icon.isError()) {
            stage.getIcons().add(icon);
        } else {
            logger.log(Level.WARNING, "Error loading app icon");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
