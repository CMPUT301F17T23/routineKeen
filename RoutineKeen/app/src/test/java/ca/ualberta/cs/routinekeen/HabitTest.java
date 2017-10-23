package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class HabitTest{    @Test
    public void testHabit() throws Exception{
    String habitTitle = "test habit title", habitReason = "test habit reason";
    Date testDate = new Date();
    Habit testHabit = new Habit(habitTitle,habitReason,testDate);

    assertTrue(testHabit.getTitle().equal(habitTitle));
    assertTrue(testHabit.getReason().equal(habitReason));
    assertTrue(testHabit.getStartDate().euqal(testDate));

    String newTitle = "new title";
    String newReason = "new reasoning";

    testHabit.setTitle = newTitle;
    testHabit.setReason = newReason;

    assertTrue(testHabit.getReason().equal(newTitle));
    assertTrue(testHabit.getStartDate().euqal(newReason));
}
}