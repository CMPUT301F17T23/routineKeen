package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class HabitUserListTest {
  @Test

  public void testHabitUserList() throws Exception {
    String testUniqueUserName = "Test user name";
    User testUser = new User(testUniqueUserName);
    assertTrue(testUser.getUserName().equal(testUniqueUserName));
    
    UserList testUserList = new UserList();
    testUserList.add(testUser);
    assertTrue(testUserList.getUser(testUser).equal(testUser);
    
    testUserList.remove(testUser);
    assertEquals(testUserList.size(), 0)
  
  }

}
