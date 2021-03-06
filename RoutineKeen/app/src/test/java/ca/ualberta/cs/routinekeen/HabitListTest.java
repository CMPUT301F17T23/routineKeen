package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;

import static org.junit.Assert.*;

public class HabitListTest{    @Test

    public void testHabitList() throws Exception{
        String habitTitle = "test habit title";
        String habitReason = "test habit reason";
        Date testDate = new Date();
        Habit testHabit = new Habit(habitTitle,habitReason,testDate);

        assertEquals(testHabit.getHabitTitle(), habitTitle);
        assertEquals(testHabit.getHabitReason(), habitReason);
        assertEquals(testHabit.getStartDate(), testDate);

        String newTitle = "another habit title";
        testHabit.setHabitTitle(newTitle);
        HabitList testHabitList= new HabitList();
        testHabitList.addHabit(testHabit);
        assertTrue(testHabitList.getHabitByType(newTitle).equals(testHabit));

    }
}