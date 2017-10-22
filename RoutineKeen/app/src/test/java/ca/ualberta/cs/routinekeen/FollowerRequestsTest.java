package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class HabitUserListTest {
  @Test
  
  public void testFollowerRequests() throwsException {
     String testUniqueUserName = "Test user name";
     User testUser = new User(testUniqueUserrName);
     assertTrue(testUser.getUserName().equal(testUniqueUserName));
     
     String testUniqueFollowerName = "Test follower name";
     User testFollower = new User(testUniqueFollowerName);
     assertTrue(testFollower.getUserName().equal(testUniqueFollowerName));
     
     testUser.getFollowerRequests().add(testFollower);
     assertTrue(testUser.getFollowersRequests().getRequests(testUniqueFollowerName).equal(testUniqueFollowerName));
     
     testUser.getFollowerRequests().remove(testFollower);
     assertEquals(testUser.getFollowerRequests.size(), 0);
    
  }

}

