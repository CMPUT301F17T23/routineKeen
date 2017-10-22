package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class HabitUserListTest {
  @Test
  
  public void testFollowerList() throws Exception {
    String testUniqueFollowerName = "Test follower name";
    User testFollower = new User(testUniqueFollowerName);
    assertTrue(testFollower.getUserName().equal(testUniqueFollowerName));
    
    FollowerList testFollowerList = new FollowerList();
    testFollowerList.add(testFollower);
    assertTrue(testFollowerList.getUser(testFollower).equal(testFollower);
    
    testFollowerList.remove(testFollower);
    assertEquals(testFollowerList.size(), 0);
  }
  
}
