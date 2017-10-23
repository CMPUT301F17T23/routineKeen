package ca.ualberta.cs.routinekeen;
import org.junit.Test;

import ca.ualberta.cs.routinekeen.Models.User;

import static org.junit.Assert.*;

public class FollowerRequestsTest {
  @Test
  
  public void testFollowerRequests() throws Exception {
     String testUniqueUserName = "Test user name";
     User testUser = new User(testUniqueUserName);
     assertTrue(testUser.getUsername().equals(testUniqueUserName));
     
     String testUniqueFollowerName = "Test follower name";
     User testFollower = new User(testUniqueFollowerName);
     assertTrue(testFollower.getUsername().equals(testUniqueFollowerName));
     
     testUser.getFollowerRequests().add(testFollower);
      // get(0) hardcoded for testing purposes
     assertTrue(testUser.getFollowerRequests().get(0).equals(testUniqueFollowerName));
     
     testUser.getFollowerRequests().remove(testFollower);
     assertEquals(testUser.getFollowerRequests().size(), 0);
  }

}

