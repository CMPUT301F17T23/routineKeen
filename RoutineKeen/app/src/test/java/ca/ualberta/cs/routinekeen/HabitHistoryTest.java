package ca.ualberta.cs.routinekeen;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;
import static org.junit.Assert.*;
public class HabitHistoryTest{
    public void testHabitHistory() throws Exception {
        String testHabitEventTitle = "Test HabitEvent title";
        String testHabitComment = "Test HabitEvent comment";
        HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle,testHabitComment);
        HabitHistory testHabitHistory = new HabitHistory();

        testHabitHistory.addHabitEvent(testHabitEvent);
        assertTrue(testHabitHistory.size().equal(1));
        assertTrue(testHabitHistory.getHabitEvent(testHabitEventTitle).equal(testHabitEvent));

        testHabitHistory.removeHabitEvent(testHabitEventTitle);
        assertTrue(testHabitHistory.size().equal(0));

        testHabitHistory.addHabitEvent(testHabitEvent);
        String testHabitFilter = "test filter Habit type";
        Collection<HabitEvent> testFilteredList = testHabitHistory.getFilterdList(testHabitFilter);
        assertTrue(testFilteredList.get(testHabitEventTitle).equal(testHabitEvent));
    }
}