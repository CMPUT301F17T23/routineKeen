package ca.ualberta.cs.routinekeen;
import org.junit.Test;

import ca.ualberta.cs.routinekeen.Models.User;

import static org.junit.Assert.*;

public class FollowerListTest {
  @Test
  
  public void testFollowerList() throws Exception {
    String testUniqueUserName = "Test User name";
    User testUser = new User(testUniqueUserrName);
    assertTrue(testUser.getUserName().equal(testUniqueUserName));
    
    String testUniqueFollowerName = "Test follower name";
    User testFollower = new User(testUniqueFollowerName);
    assertTrue(testFollower.getUserName().equal(testUniqueFollowerName));
    
    testUser.getFollowerList().add(testFollower);
    assertTrue(testUser.getFollowersList().getFollower(testUniqueFollowerName).equal(testUniqueFollowerName));
    
    testUser.getFollowerList().remove(testFollower);
    assertEquals(testUser.getFollowerList.size(), 0);
    
  }
  
}