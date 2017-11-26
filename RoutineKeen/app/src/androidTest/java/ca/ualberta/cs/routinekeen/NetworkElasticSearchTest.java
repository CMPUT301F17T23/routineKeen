package ca.ualberta.cs.routinekeen;

import android.net.Network;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.maps.model.LatLng;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import ca.ualberta.cs.routinekeen.Controllers.ElasticSearchController;
import ca.ualberta.cs.routinekeen.Controllers.NetworkDataManager;
import ca.ualberta.cs.routinekeen.Helpers.DateHelpers;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.Photo;
import ca.ualberta.cs.routinekeen.Models.User;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NetworkElasticSearchTest {

    // Test data for adding a user
    final static String testAddUsername = "testAddUsername";

    // Test data for adding a habit
    final static String testAddHabitTitle = "testAddHabitTitle";
    final static String testAddHabitReason = "testAddHabitReason";
    final static Date testAddHabitStartDate = new Date();
    final static String[] testAddHabitSchedule =  new String[] {"MON", "WED", "FRI"};
    final static String testAddHabitUserID = "AV_2RTVTm9X4YezqHBQM";

    // Test data for adding a habit event
    final static String testAddEventTitle = "testAddEventTitle";
    final static String testAddEventComment = "testAddEventComment";
    final static String testAddEventHabitType = "testAddEventHabitType";
    final static LatLng testAddEventLocation = new LatLng(-41.7, 42.5);
    final static Photo testAddEventPhoto = null;

    // Test data for getting a user
    final static String testGetUsername = "testGetUsername";

    @BeforeClass
    public static void initializeDataToRetrieveOnES(){
    }

    @Test
    public void testAddUserTask() {
        final User testUser = new User(testAddUsername);
        String userID = NetworkDataManager.AddNewUser(testUser);
        assertNotNull(userID);
    }

    @Test
    public void testGetUserTask(){
        User retrievedUser = NetworkDataManager.GetUser(testGetUsername);
        assertEquals(testGetUsername, retrievedUser.getUsername());
    }

    @Test
    public void testAddHabitTask(){
        final Habit testHabit = new Habit(testAddHabitTitle, testAddHabitReason, testAddHabitStartDate);
        ArrayList<String> testSched = new ArrayList<String>(Arrays.asList(testAddHabitSchedule));
        testHabit.setScheduledHabitDays(testSched);
        testHabit.setHabitUserID(testAddHabitUserID);
        String referenceID = NetworkDataManager.AddNewHabit(testHabit);
        assertNotNull(referenceID);
    }

    @Test
    public void testGetHabitByTitleTask() {
        assertTrue(false); // implement later
    }

    @Test
    public void testAddHabitEventTask() {
        HabitEvent testHabitEvent = new HabitEvent(testAddEventTitle, testAddEventHabitType, testAddEventComment);
        testHabitEvent.setHabitEventUserID("A3x767asdkk312djSDJAKd123");
        testHabitEvent.setLocation(testAddEventLocation);
        // testHabitEvent.setPhoto(testAddEventPhoto);
        String referenceID = NetworkDataManager.AddNewHabitEvent(testHabitEvent);
        assertNotNull(referenceID);
    }
}
