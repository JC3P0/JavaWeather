package javaweathers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WeatherTest {

    @Test
    public void testValidWeatherConstructor() {
        Weather weather = new Weather(75, 80, 70, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
        assertEquals(75, weather.getCurrentTemp());
        assertEquals(80, weather.getMaxTemp());
        assertEquals(70, weather.getMinTemp());
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
            // Weather weather = new Weather();
            Weather weather = new Weather(-200, 80, 70, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
            assertTrue(weather.isEmpty());
            assertNotNull(weather);
        });

        assertThrows(IllegalArgumentException.class, 
        () -> {
            Weather weather = new Weather(75, 200, 70, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
            assertTrue(weather.isEmpty());
            assertNotNull(weather);
        });

        assertThrows(IllegalArgumentException.class, 
        () -> {
            Weather weather = new Weather(75, 80, 70, 110, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "clear sky", "JP", "Tokyo", "01d");
            assertTrue(weather.isEmpty());
            assertNotNull(weather);
        });

        assertThrows(IllegalArgumentException.class, 
        () -> {
            Weather weather = new Weather(75, 80, 70, 50, 10, 3600, 1609459200L, 1609498800L, 1609459200L, 139.6917, 35.6895, 5.0, "", "JP", "Tokyo", "01d");
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
}
