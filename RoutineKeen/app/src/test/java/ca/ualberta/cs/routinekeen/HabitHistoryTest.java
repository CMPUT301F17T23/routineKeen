package ca.ualberta.cs.routinekeen;

import java.util.ArrayList;
import java.util.Collection;

import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;

import static org.junit.Assert.*;
public class HabitHistoryTest{
    public void testHabitHistory() throws Exception {
        String testHabitEventTitle = "Test HabitEvent title";
        String testHabitComment = "Test HabitEvent comment";
        HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle,testHabitComment);
        HabitHistory testHabitHistory = new HabitHistory();

        testHabitHistory.addHabitEvent(testHabitEvent);
        assertTrue(testHabitHistory.getSize() == 1);
        assertTrue(testHabitHistory.getHabitEventsByType(testHabitEventTitle).equals(testHabitEvent));

        testHabitHistory.removeHabitEvent(testHabitEvent);
        assertTrue(testHabitHistory.getSize() == 0);

        testHabitHistory.addHabitEvent(testHabitEvent);
        String testHabitFilter = "test filter Habit type";
//        testHabitHistory.setCurrentHabitTypeFilter(testHabitFilter);
//        ArrayList<HabitEvent> testFilteredList = (ArrayList<HabitEvent>)testHabitHistory.getFilteredList();
//        assertTrue(testFilteredList.get(0).equals(testHabitEvent));
    }
}