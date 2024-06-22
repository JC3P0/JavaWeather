package javaweathers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ForecastTest {

    @Test
    public void testDefaultConstructor() {
        Forecast forecast = new Forecast();
        assertNotNull(forecast.getDays());
        assertTrue(forecast.getDays().isEmpty());
        assertEquals(0, forecast.getLat());
        assertEquals(0, forecast.getLon());
        assertEquals(0, forecast.getTimeZone());
    }

    @Test
    public void testParameterizedConstructor() {
        List<Day> days = new ArrayList<>();
        days.add(new Day(1609459200L, 75, 55, "clear sky", "01d"));
        days.add(new Day(1609545600L, 80, 60, "partly cloudy", "02d"));

        Forecast forecast = new Forecast(35.6895, 139.6917, 3600, days);

        assertEquals(35.6895, forecast.getLat());
        assertEquals(139.6917, forecast.getLon());
        assertEquals(3600, forecast.getTimeZone());
        assertNotNull(forecast.getDays());
        assertEquals(2, forecast.getDays().size());
        assertEquals(75, forecast.getDays().get(0).getHighTemp());
        assertEquals("clear sky", forecast.getDays().get(0).getDescription());
    }

    @Test
    public void testInvalidLatLon() {
        Forecast forecast = Forecast.fetchForecastForLocation(-91, 0);
        assertNotNull(forecast);
        assertTrue(forecast.isEmpty());

        forecast = Forecast.fetchForecastForLocation(0, -181);
        assertNotNull(forecast);
        assertTrue(forecast.isEmpty());

        forecast = Forecast.fetchForecastForLocation(91, 0);
        assertNotNull(forecast);
        assertTrue(forecast.isEmpty());

        forecast = Forecast.fetchForecastForLocation(0, 181);
        assertNotNull(forecast);
        assertTrue(forecast.isEmpty());
    }

    @Test
    public void testEmptyDaysList() {
        Forecast forecast = new Forecast(35.6895, 139.6917, 3600, new ArrayList<>());
        assertNotNull(forecast.getDays());
        assertTrue(forecast.getDays().isEmpty());
    }

    @Test
    public void testBadDataInParameterizedConstructor() {
        assertThrows(IllegalArgumentException.class, 
        () -> {
            Forecast forecast = new Forecast(35.6895, 139.6917, 3600, null);
            assertTrue(forecast.isEmpty());
            assertNotNull(forecast);
        });
    }
}
