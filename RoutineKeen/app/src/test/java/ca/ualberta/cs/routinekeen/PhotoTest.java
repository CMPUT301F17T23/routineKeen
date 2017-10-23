package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class testLocation {
  @Test
  
  public void testPhoto() throws Exception {
    String testHabitEventTitle = "Test HabitEvent title";
    HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle);
    
    byte[] testByteArray = new BigInteger("1111000011110000", 2).toByteArray();
    testHabitEvent.addPhoto(testByteArray);
    assertArrayEquals(testHabitEvent.getImage(), testByteArray);
    
    testHabitEvent.deleteImage();
    assertNull(testHabitEvent.getImage());

  }
  
}
