package ca.ualberta.cs.routinekeen;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import ca.ualberta.cs.routinekeen.Models.HabitEvent;

import static org.junit.Assert.*;

public class LocationTest {
    @Test
    public void LocationTest() throws Exception {
        String testHabitEventTitle = "Test HabitEvent title";
        LatLng testLocation = new LatLng(-41.2, 42.5);
        String testHabitType = "test habit type";
        HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle,testHabitType);

        testHabitEvent.setLocation(testLocation);
        assertTrue(testHabitEvent.getLocation().equals(testLocation));
    }
  
}
