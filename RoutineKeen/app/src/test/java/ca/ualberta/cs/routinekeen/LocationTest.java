package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class testLocation {
  @Test
  
  public void testLocation() throws Exception {
    String testHabitEventTitle = "Test HabitEvent title";
    HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle);
    
    String testLocation = "Test Location";
    testHabitEvent.addLocation(testLocation);
    assertTrue(testHabitEvent.getLocation().equal(testLocation);
    
    testHabitEvent.removeLocation(testLocation);
    assertTrue(testHabitEvent.getLocation().equal("");
  
  }
  
}
