package ca.ualberta.cs.routinekeen;
import org.junit.Test;

import java.util.Date;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.User;

import static org.junit.Assert.*;

/**
 * Created by Saddog on 10/21/2017.
 */

public class UserTest {
    @Test
    public void testUser() throws Exception{
        String testUniqueUserName = "Test username";
        String testUniqueFollowerName = "Test follower name";
        User testUser = new User(testUniqueUserName);
        User testFollower = new User(testUniqueFollowerName);
        assertTrue(testUser.getUsername().equals(testUniqueUserName));
        assertTrue(testFollower.getUsername().equals(testUniqueFollowerName));

    }
}
