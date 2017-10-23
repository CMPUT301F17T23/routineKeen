package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;

import ca.ualberta.cs.routinekeen.Models.Habit;

import static org.junit.Assert.*;

public class HabitTest{    @Test
    public void testHabit() throws Exception{
    String habitTitle = "test habit title", habitReason = "test habit reason";
    Date startDate = new Date();
    Habit testHabit = new Habit(habitTitle,habitReason,startDate);

    assertTrue(testHabit.getHabitTitle().equals(habitTitle));
    assertTrue(testHabit.getHabitReason().equals(habitReason));
    assertTrue(testHabit.getStartDate().equals(startDate));

    String newTitle = "new title";
    String newReason = "new reasoning";

    testHabit.setHabitTitle(newTitle);
    testHabit.setHabitReason(newReason);

    assertTrue(testHabit.getHabitTitle().equals(newTitle));
    assertTrue(testHabit.getHabitReason().equals(newReason));
}
}