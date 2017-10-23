package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;

import ca.ualberta.cs.routinekeen.Models.Habit;

import static org.junit.Assert.*;

public class HabitTest{    @Test
    public void testHabit() throws Exception{
    String habitTitle = "test habit title", habitReason = "test habit reason";
    Date testDate = new Date();
    Habit testHabit = new Habit(habitTitle,habitReason,testDate);

    assertTrue(testHabit.getTitle().equals(habitTitle));
    assertTrue(testHabit.getReason().equals(habitReason));
    assertTrue(testHabit.getStartDate().equals(testDate));

    String newTitle = "new title";
    String newReason = "new reasoning";

    testHabit.setTitle = newTitle;
    testHabit.setReason = newReason;

    assertTrue(testHabit.getReason().equals(newTitle));
    assertTrue(testHabit.getStartDate().equals(newReason));
}
}