package ca.ualberta.cs.routinekeen;
import java.util.Date;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.User;

import static org.junit.Assert.*;

/**
 * Created by Saddog on 10/21/2017.
 */

public class UserTest {
    public void testUser() throws Exception{
        String testUniqueUserName = "Test username";
        String testUniqueFollowerName = "Test follower name";
        User testUser = new User(testUniqueUserName);
        User testFollower = new User(testUniqueFollowerName);
        assertTrue(testUser.getUsername().equals(testUniqueUserName));
        assertTrue(testFollower.getUsername().equals(testUniqueFollowerName));

        String habitTitle = "test habit title", habitReason = "test habit reason";
        Date testDate = new Date();
        Habit testHabit = new Habit(habitTitle,habitReason,testDate);

        testUser.getHabits().add(testHabit);
        assertTrue(testUser.getHabits().size() == 1);
        assertTrue(testUser.getHabitEvents().size() == 0);

        testUser.getFollowerRequests().add(testFollower);
        testUser.getFollowerList().add(testFollower);
        // get(0) hardcoded for testing purposes
        assertTrue(testUser.getFollowerRequests().get(0).equals(testUniqueFollowerName));
        assertTrue(testUser.getFollowerList().get(0).equals(testUniqueFollowerName));
    }
}
