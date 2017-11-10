package ca.ualberta.cs.routinekeen;




import junit.framework.TestCase;

import java.util.Date;
import ca.ualberta.cs.routinekeen.Controllers.UserDataController;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;

/**
 * Created by Saddog on 11/9/2017.
 */

public class UserDataTest extends TestCase {
    public void testUserDataController(){
        HabitList testHabitList = new UserDataController().getUserHabitList();
        String habitTitle = "test habit title";
        String habitReason = "test habit reason";
        Date testDate = new Date();
        Habit testHabit = new Habit(habitTitle,habitReason,testDate);
        testHabitList.addHabit(testHabit);
    }
}
