package ca.ualberta.cs.routinekeen;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import java.math.BigInteger;

import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.Photo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class PhotoTest {
  @Test
  
  public void PhotoTest() throws Exception {
    String testHabitEventTitle = "Test HabitEvent title";
    HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle);
    
    Photo testPhoto = new Photo(new BigInteger("1111000011110000", 2).toByteArray());
    testHabitEvent.setPhoto(testPhoto);
    assertEquals(testHabitEvent.getPhoto(), testPhoto);
    
    testHabitEvent.deleteImage();
    assertThat(testHabitEvent.getPhoto(), not(equalTo(testPhoto)));
  }



}
