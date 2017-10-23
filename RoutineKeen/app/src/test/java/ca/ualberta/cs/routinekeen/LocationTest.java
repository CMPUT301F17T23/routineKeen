package ca.ualberta.cs.routinekeen;
import org.junit.Test;

import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitLocation;

import static org.junit.Assert.*;

public class LocationTest {
  @Test
  
  public void LocationTest() throws Exception {
    String testHabitEventTitle = "Test HabitEvent title";
    HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle);

    HabitLocation testLocation = new HabitLocation("Test Location");
    testHabitEvent.setLocation(testLocation);
    assertTrue(testHabitEvent.getLocation().equals(testLocation));
  }
  
}
