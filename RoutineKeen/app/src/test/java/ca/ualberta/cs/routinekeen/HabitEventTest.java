package ca.ualberta.cs.routinekeen;

import ca.ualberta.cs.routinekeen.Models.HabitEvent;

import static org.junit.Assert.*;
public class HabitEventTest {
    public void testHabitEvent() throws Exception {
        String testHabitEventTitle = "Test HabitEvent title";
        String testHabitComment = "Test HabitEvent comment";
        HabitEvent testHabitEvent = new HabitEvent(testHabitEventTitle,testHabitComment);
        assertTrue(testHabitEvent.getEventTitle().equal(testHabitEventTitle));
        assertTrue(testHabitEvent.getEventComment().equal(testHabitComment));

        String newHabitEventTitle = "New HabitEvent title";
        String newHabitEventComment = "New HabitEvent comment";
        testHabitEvent.setEventTitle(newHabitEventTitle);
        testHabitEvent.setEventComment(newHabitEventComment);
        assertTrue(testHabitEvent.getEventTitle().equal(newHabitEventTitle));
        assertTrue(testHabitEvent.getEventComment().equal(newHabitComment));
    }
}
