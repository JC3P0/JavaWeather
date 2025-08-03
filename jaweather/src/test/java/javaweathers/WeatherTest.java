package javaweathers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WeatherTest {

    @Test
    public void testValidWeatherConstructor() {
        Weather weather = new Weather(75, 80, 70, 75, 80, 70, 24, 27, 21, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
        assertEquals(75, weather.getCurrentTemp());
        assertEquals(80, weather.getMaxTemp());
        assertEquals(70, weather.getMinTemp());
        assertEquals(75, weather.getCurrentTempF());
        assertEquals(80, weather.getMaxTempF());
        assertEquals(70, weather.getMinTempF());
        assertEquals(24, weather.getCurrentTempC());
        assertEquals(27, weather.getMaxTempC());
        assertEquals(21, weather.getMinTempC());
        assertEquals(50, weather.getHumidity());
        assertEquals(10, weather.getVisibility());
        assertEquals("clear sky", weather.getDescription());
        assertEquals("JP", weather.getCountry());
        assertEquals("Tokyo", weather.getName());
        assertEquals("01d", weather.getIcon());
    }

    @Test
    public void testInvalidWeatherConstructor() {
        assertThrows(IllegalArgumentException.class, 
        () -> {
            // Test invalid current temperature
            Weather weather = new Weather(-200, 80, 70, -200, 80, 70, -129, 27, 21, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
            assertTrue(weather.isEmpty());
            assertNotNull(weather);
        });

        assertThrows(IllegalArgumentException.class, 
        () -> {
            // Test invalid max temperature
            Weather weather = new Weather(75, 200, 70, 75, 200, 70, 24, 93, 21, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
            assertTrue(weather.isEmpty());
            assertNotNull(weather);
        });

        assertThrows(IllegalArgumentException.class, 
        () -> {
            // Test invalid humidity
            Weather weather = new Weather(75, 80, 70, 75, 80, 70, 24, 27, 21, 110, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
            assertTrue(weather.isEmpty());
            assertNotNull(weather);
        });

        assertThrows(IllegalArgumentException.class, 
        () -> {
            // Test empty description
            Weather weather = new Weather(75, 80, 70, 75, 80, 70, 24, 27, 21, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "", "JP", "Tokyo", "01d");
            assertTrue(weather.isEmpty());
            assertNotNull(weather);
        });
    }

    @Test
    public void testNonexistentCityFetchWeatherForCity() {
        Weather weather = Weather.fetchWeatherForCity("Abc123NonExistentCity");
        assertNotNull(weather);
        assertTrue(weather.isEmpty());
    }

    @Test
    public void testEmptyCityFetchWeatherForCity() {
        Weather weather = Weather.fetchWeatherForCity("");
        assertNotNull(weather); // Check that the weather object is not null
        assertTrue(weather.isEmpty()); // Check that the weather object is empty
    }

    @Test
    public void testTemperatureConversions() {
        // Test with known temperature values
        // 300K = 26.85°C = 80.33°F
        // 310K = 36.85°C = 98.33°F
        // 290K = 16.85°C = 62.33°F
        Weather weather = new Weather(80, 98, 62, 80, 98, 62, 27, 37, 17, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
        
        // Test Fahrenheit temperatures
        assertEquals(80, weather.getCurrentTempF());
        assertEquals(98, weather.getMaxTempF());
        assertEquals(62, weather.getMinTempF());
        
        // Test Celsius temperatures
        assertEquals(27, weather.getCurrentTempC());
        assertEquals(37, weather.getMaxTempC());
        assertEquals(17, weather.getMinTempC());
    }

    @Test
    public void testTemperatureGetters() {
        Weather weather = new Weather(75, 85, 65, 75, 85, 65, 24, 29, 18, 60, 15, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "partly cloudy", "US", "New York", "02d");
        
        // Test all temperature getters
        assertEquals(75, weather.getCurrentTempF());
        assertEquals(85, weather.getMaxTempF());
        assertEquals(65, weather.getMinTempF());
        assertEquals(24, weather.getCurrentTempC());
        assertEquals(29, weather.getMaxTempC());
        assertEquals(18, weather.getMinTempC());
        
        // Verify legacy getCurrentTemp still works (should return Fahrenheit by default)
        assertEquals(75, weather.getCurrentTemp());
        assertEquals(85, weather.getMaxTemp());
        assertEquals(65, weather.getMinTemp());
    }

    @Test
    public void testTemperatureValidationRanges() {
        // Test that temperature validation works for both Fahrenheit and Celsius ranges
        // Valid temperatures should not throw exceptions
        assertDoesNotThrow(() -> {
            new Weather(-10, 20, -20, -10, 20, -20, -23, -7, -29, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "cold weather", "CA", "Alaska", "13d");
        });
        
        assertDoesNotThrow(() -> {
            new Weather(100, 110, 90, 100, 110, 90, 38, 43, 32, 30, 5, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "hot weather", "AE", "Dubai", "01d");
        });
    }

    @Test
    public void testFreezingAndBoilingPoints() {
        // Test temperatures around water's freezing and boiling points
        // 32°F = 0°C, 212°F = 100°C
        Weather freezingWeather = new Weather(32, 35, 30, 32, 35, 30, 0, 2, -1, 80, 8, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "freezing", "NO", "Oslo", "13d");
        Weather boilingWeather = new Weather(150, 150, 150, 150, 150, 150, 66, 66, 66, 10, 20, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "very hot", "SA", "Riyadh", "01d");
        
        // Test freezing point temperatures
        assertEquals(32, freezingWeather.getCurrentTempF());
        assertEquals(0, freezingWeather.getCurrentTempC());
        
        // Test very hot temperatures
        assertEquals(150, boilingWeather.getCurrentTempF());
        assertEquals(66, boilingWeather.getCurrentTempC());
    }
}
