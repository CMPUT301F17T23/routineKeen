package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class LocationListTest {
  @Test
  
  public void testLocationList() throws Exception {
    String testHabitEventTitle = "Test HabitEvent title";
    HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle);
    assertTrue(testHabitEvent.getEventTitle().equal(testHabitEventTitle));
    
    String testLocation = "Test Location";
    testHabitEvent.addLocation(testLocation);
    assertTrue(testHabitEvent.getLocation().equal(testLocation);
    
    //....
    /*
    Not sure how this one works.
    */
  
  }
}
