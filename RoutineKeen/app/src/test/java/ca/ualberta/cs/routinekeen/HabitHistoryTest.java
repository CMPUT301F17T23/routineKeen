package ca.ualberta.cs.routinekeen;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;

import static org.junit.Assert.*;
public class HabitHistoryTest{
    @Test
    public void testHabitHistory() throws Exception {
        String testHabitEventTitle = "Test HabitEvent title";
        String testHabitComment = "Test HabitEvent comment";
        HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle,testHabitComment);
        HabitHistory testHabitHistory = new HabitHistory();

        testHabitHistory.addHabitEvent(testHabitEvent);
        assertTrue(testHabitHistory.getSize() == 1);

        testHabitHistory.removeHabitEvent(testHabitEvent);
        assertTrue(testHabitHistory.getSize() == 0);

    }
}