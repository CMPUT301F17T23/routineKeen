package ca.ualberta.cs.routinekeen;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class HabitTest{    @Test
    public void testHabit(){
    String habitTitle = "test habit title", habitReason = "test habit reason";
    Date testDate = new Date();
    Habit testHabit = new Habit(habitTitle,habitReason,testDate);

    assertTrue(testHabit.getHabitTitle().equal(habitTitle));
    assertTrue(testHabit.getHabitReason().equal(habitReason));
    assertTrue(testHabit.getHabitStartDate().euqal(testDate));

    String newTitle = "new title";
    String newReason = "new reasoning";

    testHabit.setTitle = newTitle;
    testHabit.setReason = newReason;

    assertTrue(testHabit.getHabitReason().equal(newTitle));
    assertTrue(testHabit.getHabitStartDate().euqal(newReason));

    HabitList testHabitList= new HabitList();
    testHabitList.add(testHabit);
    assertTrue(testHabitList.getHabit(newTitle).equal(testHabit));
    testHabitList.remove(newTitle);
    assertTrue(testHabitList.size().euqal(0));


}
}