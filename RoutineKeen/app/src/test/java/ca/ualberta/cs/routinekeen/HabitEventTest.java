package ca.ualberta.cs.routinekeen;

import org.junit.Test;

import ca.ualberta.cs.routinekeen.Models.HabitEvent;

import static org.junit.Assert.*;
public class HabitEventTest {

    @Test
    public void testHabitEvent() throws Exception {
        String testHabitEventTitle = "Test HabitEvent title";
        String testHabitComment = "Test HabitEvent comment";
        HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle,testHabitComment);
        assertTrue(testHabitEvent.getTitle().equals(testHabitEventTitle));
        assertTrue(testHabitEvent.getComment().equals(testHabitComment));

        String newHabitEventTitle = "New HabitEvent title";
        String newHabitEventComment = "New HabitEvent comment";
        testHabitEvent.setTitle(newHabitEventTitle);
        testHabitEvent.setComment(newHabitEventComment);
        assertTrue(testHabitEvent.getTitle().equals(newHabitEventTitle));
        assertTrue(testHabitEvent.getComment().equals(newHabitEventComment));
    }
}
