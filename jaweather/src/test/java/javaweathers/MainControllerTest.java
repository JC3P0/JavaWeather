package javaweathers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainControllerTest extends ApplicationTest {

    private MainController controller;
    private Button temp0, temp1, temp2;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javaweathers/main-view.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        // Get temperature buttons using reflection for testing
        temp0 = (Button) lookup("#temp0").query();
        temp1 = (Button) lookup("#temp1").query();
        temp2 = (Button) lookup("#temp2").query();
    }

    @BeforeEach
    public void setUp() {
        // Initialize test weather data for testing temperature toggle
        Platform.runLater(() -> {
            try {
                // Use reflection to access static weather objects and set test data
                Field weathers0Field = MainController.class.getDeclaredField("weathers0");
                Field weathers1Field = MainController.class.getDeclaredField("weathers1");
                weathers0Field.setAccessible(true);
                weathers1Field.setAccessible(true);
                
                Weather testWeather0 = new Weather(75, 80, 70, 75, 80, 70, 24, 27, 21, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
                Weather testWeather1 = new Weather(68, 72, 65, 68, 72, 65, 20, 22, 18, 60, 8, 3600, 1609459200L, 1609498800L, 1609459200L, -74.0060, 40.7128, 3.5, "partly cloudy", "US", "New York", "02d");
                
                weathers0Field.set(null, testWeather0);
                weathers1Field.set(null, testWeather1);
                
                // Reset temperature unit preferences to default (Fahrenheit)
                Field useFahrenheitField = MainController.class.getDeclaredField("useFahrenheit");
                useFahrenheitField.setAccessible(true);
                boolean[] useFahrenheit = {true, true, true};
                useFahrenheitField.set(null, useFahrenheit);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testTemperatureTogglePanel0() {
        Platform.runLater(() -> {
            try {
                // Ensure weather data is properly set
                Field weathers0Field = MainController.class.getDeclaredField("weathers0");
                weathers0Field.setAccessible(true);
                Weather testWeather = new Weather(75, 80, 70, 75, 80, 70, 24, 27, 21, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
                weathers0Field.set(null, testWeather);
                
                // Set initial state (Fahrenheit)
                temp0.setText("75˚F");
                temp0.setVisible(true);
                
                // Test toggle from Fahrenheit to Celsius
                temp0.fire();
                assertEquals("24˚C", temp0.getText());
                
                // Test toggle back to Fahrenheit
                temp0.fire();
                assertEquals("75˚F", temp0.getText());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testTemperatureTogglePanel1() {
        Platform.runLater(() -> {
            try {
                // Ensure weather data is properly set
                Field weathers1Field = MainController.class.getDeclaredField("weathers1");
                weathers1Field.setAccessible(true);
                Weather testWeather = new Weather(68, 72, 65, 68, 72, 65, 20, 22, 18, 60, 8, 3600, 1609459200L, 1609498800L, 1609459200L, -74.0060, 40.7128, 3.5, "partly cloudy", "US", "New York", "02d");
                weathers1Field.set(null, testWeather);
                
                // Set initial state (Fahrenheit)
                temp1.setText("68˚F");
                temp1.setVisible(true);
                
                // Test toggle from Fahrenheit to Celsius
                temp1.fire();
                assertEquals("20˚C", temp1.getText());
                
                // Test toggle back to Fahrenheit
                temp1.fire();
                assertEquals("68˚F", temp1.getText());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testIndependentTemperatureToggles() {
        Platform.runLater(() -> {
            try {
                // Setup weather data for both panels
                Field weathers0Field = MainController.class.getDeclaredField("weathers0");
                Field weathers1Field = MainController.class.getDeclaredField("weathers1");
                weathers0Field.setAccessible(true);
                weathers1Field.setAccessible(true);
                Weather testWeather0 = new Weather(75, 80, 70, 75, 80, 70, 24, 27, 21, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
                Weather testWeather1 = new Weather(68, 72, 65, 68, 72, 65, 20, 22, 18, 60, 8, 3600, 1609459200L, 1609498800L, 1609459200L, -74.0060, 40.7128, 3.5, "partly cloudy", "US", "New York", "02d");
                weathers0Field.set(null, testWeather0);
                weathers1Field.set(null, testWeather1);
                
                // Setup both panels
                temp0.setText("75˚F");
                temp1.setText("68˚F");
                temp0.setVisible(true);
                temp1.setVisible(true);
                
                // Toggle panel 0 to Celsius
                temp0.fire();
                assertEquals("24˚C", temp0.getText());
                // Panel 1 should remain in Fahrenheit
                assertEquals("68˚F", temp1.getText());
                
                // Toggle panel 1 to Celsius
                temp1.fire();
                assertEquals("20˚C", temp1.getText());
                // Panel 0 should remain in Celsius
                assertEquals("24˚C", temp0.getText());
                
                // Toggle panel 0 back to Fahrenheit
                temp0.fire();
                assertEquals("75˚F", temp0.getText());
                // Panel 1 should remain in Celsius
                assertEquals("20˚C", temp1.getText());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testTemperatureUnitPersistence() throws Exception {
        // Test that temperature unit preferences are maintained
        Field useFahrenheitField = MainController.class.getDeclaredField("useFahrenheit");
        useFahrenheitField.setAccessible(true);
        boolean[] useFahrenheit = (boolean[]) useFahrenheitField.get(null);
        
        Platform.runLater(() -> {
            try {
                // Initially should be all Fahrenheit
                assertTrue(useFahrenheit[0]);
                assertTrue(useFahrenheit[1]);
                assertTrue(useFahrenheit[2]);
                
                // Setup weather data and toggle panel 0
                Field weathers0Field = MainController.class.getDeclaredField("weathers0");
                weathers0Field.setAccessible(true);
                Weather testWeather = new Weather(75, 80, 70, 75, 80, 70, 24, 27, 21, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
                weathers0Field.set(null, testWeather);
                
                temp0.setText("75˚F");
                temp0.setVisible(true);
                temp0.fire(); // Toggle to Celsius
                
                // Check that useFahrenheit array is updated
                assertFalse(useFahrenheit[0]);
                assertTrue(useFahrenheit[1]); // Should remain unchanged
                assertTrue(useFahrenheit[2]); // Should remain unchanged
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testEmptyWeatherTemperatureToggle() {
        Platform.runLater(() -> {
            try {
                // Test that temperature toggle doesn't work with empty weather
                Weather emptyWeather = new Weather();
                Field weathers2Field = MainController.class.getDeclaredField("weathers2");
                weathers2Field.setAccessible(true);
                weathers2Field.set(null, emptyWeather);
                
                // Temperature button should not be visible for empty weather
                temp2.setVisible(false);
                
                // Attempt to fire - should not change anything
                String originalText = temp2.getText();
                temp2.fire();
                assertEquals(originalText, temp2.getText());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testClearLocationResetsTemperatureUnit() throws Exception {
        Field useFahrenheitField = MainController.class.getDeclaredField("useFahrenheit");
        useFahrenheitField.setAccessible(true);
        boolean[] useFahrenheit = (boolean[]) useFahrenheitField.get(null);
        
        Platform.runLater(() -> {
            try {
                // Setup panel 0 with weather data and toggle to Celsius  
                Field weathers0Field = MainController.class.getDeclaredField("weathers0");
                weathers0Field.setAccessible(true);
                Weather testWeather = new Weather(75, 80, 70, 75, 80, 70, 24, 27, 21, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
                weathers0Field.set(null, testWeather);
                
                temp0.setText("75˚F");
                temp0.setVisible(true);
                temp0.fire(); // Toggle to Celsius
                
                // Verify it's in Celsius mode
                assertFalse(useFahrenheit[0]);
                assertEquals("24˚C", temp0.getText());
                
                // Simulate clearing the panel by calling clearPanel through reflection
                Method clearPanelMethod = MainController.class.getDeclaredMethod("clearPanel", int.class);
                clearPanelMethod.setAccessible(true);
                clearPanelMethod.invoke(controller, 0);
                
                // Verify temperature unit preference is reset to Fahrenheit
                assertTrue(useFahrenheit[0]);
                
                // Verify weather data is cleared  
                Weather clearedWeather = (Weather) weathers0Field.get(null);
                assertTrue(clearedWeather.isEmpty());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testTemperatureUnitAfterReAddingLocation() throws Exception {
        Field useFahrenheitField = MainController.class.getDeclaredField("useFahrenheit");
        useFahrenheitField.setAccessible(true);
        boolean[] useFahrenheit = (boolean[]) useFahrenheitField.get(null);
        
        Platform.runLater(() -> {
            try {
                // Setup panel 1 and toggle to Celsius
                Field weathers1Field = MainController.class.getDeclaredField("weathers1");
                weathers1Field.setAccessible(true);
                Weather testWeather1 = new Weather(68, 72, 65, 68, 72, 65, 20, 22, 18, 60, 8, 3600, 1609459200L, 1609498800L, 1609459200L, -74.0060, 40.7128, 3.5, "partly cloudy", "US", "New York", "02d");
                weathers1Field.set(null, testWeather1);
                
                temp1.setText("68˚F");
                temp1.setVisible(true);
                temp1.fire(); // Toggle to Celsius
                assertFalse(useFahrenheit[1]);
                
                // Clear the panel
                Method clearPanelMethod = MainController.class.getDeclaredMethod("clearPanel", int.class);
                clearPanelMethod.setAccessible(true);
                clearPanelMethod.invoke(controller, 1);
                
                // Unit preference should be reset to Fahrenheit
                assertTrue(useFahrenheit[1]);
                
                // Add new weather data
                Weather newWeather = new Weather(82, 87, 78, 82, 87, 78, 28, 31, 26, 45, 12, 3600, 1609459200L, 1609498800L, 1609459200L, -118.2437, 34.0522, 4.2, "sunny", "US", "Los Angeles", "01d");
                weathers1Field.set(null, newWeather);
                
                // Temperature should display in Fahrenheit by default after re-adding
                temp1.setText("82˚F");
                temp1.setVisible(true);
                assertTrue(useFahrenheit[1]);
                
                // Verify we can still toggle the new location
                temp1.fire(); // Should toggle to Celsius
                assertFalse(useFahrenheit[1]);
                assertEquals("28˚C", temp1.getText());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
