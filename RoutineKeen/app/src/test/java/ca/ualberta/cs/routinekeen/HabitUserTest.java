package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

/**
 * Created by Saddog on 10/21/2017.
 */

public class HabitUserTest {
    public void testUser() throws Exception{
        String testUniqueUserName = "Test user name";
        String testUniqueFollowerName = "Test follower name";
        User testUser = new User(testUniqueUserName);
        User testFollower = new User(testUniqueFollowerName);
        assertTrue(testUser.getUserName().equal(testUniqueUserName));
        assertTrue(testFollower.getUserName().equal(testUniqueFollowerName));

        String habitTitle = "test habit title", habitReason = "test habit reason";
        Date testDate = new Date();
        Habit testHabit = new Habit(habitTitle,habitReason,testDate);

        testUser.getHabits().add(testHabit);
        assertTrue(testUser.getHabits().size().equal(1));
        assertTrue(testUser.getHabitEvents.size().equal(0));

        testUser.getFollowerRequests().add(testFollower);
        testUser.getFollowersList().add(testFollower);
        assertTrue(testUser.getFollwersRequests()
                .getRequest(testUniqueFollowerName).euqal(testUniqueFollowerName));
        assertTrue(testUser.getFollwersList()
                .getFollower(testUniqueFollowerName).euqal(testUniqueFollowerName));
    }
}
