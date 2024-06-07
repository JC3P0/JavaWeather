package javaweathers;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
//        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApplication.class.getResource("AddLocationScene.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Weather App");
        stage.setScene(scene);
        stage.setResizable(false); //disable Resizable window
        stage.show();
    }

    public static void main(String[] args) {
        // System.out.println("[OUTPUT USED FOR DEBUGGING PURPOSES]");
        launch();
    }
}

/**
 * JavaFX App
 */
// public class MainApplication extends Application {

//     private static Scene scene;

//     @Override
//     public void start(Stage stage) throws IOException {
//         scene = new Scene(loadFXML("primary"), 640, 480);
//         stage.setScene(scene);
//         stage.show();
//     }

//     static void setRoot(String fxml) throws IOException {
//         scene.setRoot(loadFXML(fxml));
//     }

//     private static Parent loadFXML(String fxml) throws IOException {
//         FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml + ".fxml"));
//         return fxmlLoader.load();
//     }

//     public static void main(String[] args) {
//         launch();
//     }

// }