package javaweathers;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApplication extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {

        Image icon = new Image(getClass().getResourceAsStream("/javaweathers/icons/app_icon.png"));
        if (icon.isError()) {
            System.out.println("Error loading icon");
            return;
        } 

        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root, 900, 600);
        stage.getIcons().add(icon);
        stage.setTitle("JavaWeather");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
