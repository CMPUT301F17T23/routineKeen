package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class HabitListTest{    @Test

    public void testHabitList() throws Exception{
        String habitTitle = "test habit title";
        String habitReason = "test habit reason";
        Date testDate = new Date();
        Habit testHabit = new Habit(habitTitle,habitReason,testDate);

        assertTrue(testHabit.getHabitTitle().equal(habitTitle));
        assertTrue(testHabit.getHabitReason().equal(habitReason));
        assertTrue(testHabit.getHabitStartDate().euqal(testDate));

        String newTitle = "another habit title";
        testHabit.setTitle(newTitle);
        HabitList testHabitList= new HabitList();
        testHabitList.add(testHabit);
        assertTrue(testHabitList.getHabit(newTitle).equal(testHabit));

        testHabitList.remove(newTitle);
        assertTrue(testHabitList.size().euqal(0));
    }
}