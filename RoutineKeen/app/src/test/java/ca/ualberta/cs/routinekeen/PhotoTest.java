package ca.ualberta.cs.routinekeen;
import org.junit.Test;

import java.math.BigInteger;

import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.Photo;

import static org.junit.Assert.*;

public class PhotoTest {
  @Test
  
  public void PhotoTest() throws Exception {
    String testHabitEventTitle = "Test HabitEvent title";
    HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle);
    
    Photo testPhoto = new Photo(BigInteger("1111000011110000", 2).toByteArray());
    testHabitEvent.setPhoto(testByteArray);
    assertArrayEquals(testHabitEvent.getPhoto(), testByteArray);
    
    testHabitEvent.deleteImage();
    assertThat(testHabitEvent.getImage(), not(equalTo(testByteArray)));

  }
  
}
