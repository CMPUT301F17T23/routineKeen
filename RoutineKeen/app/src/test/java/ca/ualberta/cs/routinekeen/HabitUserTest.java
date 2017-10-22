package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

/**
 * Created by Saddog on 10/21/2017.
 */

public class HabitUserTest {
    public void testUser () throws Exception{
        String testUniqueUserName = "Test user name";
        String testUniqueFollowerName = "Test follower name";
        User testUser = new User(testUniqueUserName);
        User testFollower = new User(testUniqueFollowerName);
        assertTrue(testUser.getUserName().equal(testUniqueUserName));
        assertTrue(testFollower.getUserName().equal(testUniqueFollowerName));

    }
}
