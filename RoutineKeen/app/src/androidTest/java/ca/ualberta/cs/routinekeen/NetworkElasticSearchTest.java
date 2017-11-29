package ca.ualberta.cs.routinekeen;

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
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.Models.Photo;
import ca.ualberta.cs.routinekeen.Models.User;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NetworkElasticSearchTest {

    /**
     * The following data is for testing the functionality of adding/putting
     * information to be stored on the network.
     */

    final static String testAddUsername = "testAddUsername";

    private final static class AddHabitTestData{
        static String testAddHabitTitle = "testAddHabitTitle";
        static String testAddHabitReason = "testAddHabitReason";
        static Date testAddHabitStartDate = new Date();
        static String[] testAddHabitSchedule =  new String[] {"Mon", "Wed", "Fri"};
        static String testAddHabitUserID = "AV_2RTVTm9X4YezqHBQM";

    }

    private final static class AddHabitEventTestData {
        static String testAddEventTitle = "testAddEventTitle";
        static String testAddEventComment = "testAddEventComment";
        static String testAddEventHabitType = "testAddEventHabitType";
        static LatLng testAddEventLocation = new LatLng(-41.7, 42.5);
        static Photo testAddEventPhoto = null;
    }

    /**
     * The following data is for testing the functionality of getting
     * information like users, habits, and habit events from the network.
     * This data needs to be created prior to running these tests. As of right now,
     * this is currently done by just using post requests with the following values
     * for whatever type of data you need to store (i.e., user, habit, event). A better
     * way might be to write a curl script to be executed in the start up of these tests.
     */

    final static String testGetUsername = "testGetUsername";
    final static String testGetUserID = "AV_7n2-eZw0egSlVI5K0";

    // Test data for getting a habit
    private final static class GetHabitTestDataOne{
        final static String testGetHabitType = "testGetHabitType";
        final static String testGetHabitId = "AWADvALrBOIa5W1F-q6m";
        final static String testGetHabitReason = "test get reason";
        final static Date testGetHabitStartDate = DateHelpers.removeTime(DateHelpers.formatStringToDate("2017-11-28","yyyy-MM-dd"));
        final static String[] testGetHabitSched = new String [] {"Mon", "Tue", "Wed"};
    }

    private final static class GetHabitTestDataTwo{
        final static String testGetHabitType = "testGetHabitTypeTwo";
        final static String testGetHabitId = "AcaRvALrBOIa5W1F-h69";
        final static String testGetHabitReason = "test get reason two";
        final static Date testGetHabitStartDate = DateHelpers.removeTime(DateHelpers.formatStringToDate("2018-11-29","yyyy-MM-dd"));
        final static String[] testGetHabitSched = new String [] {"Mon", "Thu", "Fri"};
    }

    private final static class GetHabitEventTestData {

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
        String title = AddHabitTestData.testAddHabitTitle;
        String reason = AddHabitTestData.testAddHabitReason;
        Date startDate = AddHabitTestData.testAddHabitStartDate;
        String[] schedule = AddHabitTestData.testAddHabitSchedule;
        String associatedUserID = AddHabitTestData.testAddHabitUserID;
        final Habit testHabit = new Habit(title, reason, startDate);
        ArrayList<String> testSched = new ArrayList<String>(Arrays.asList(schedule));
        testHabit.setScheduledHabitDays(testSched);
        testHabit.setAssociatedUserID(associatedUserID);
        String referenceID = NetworkDataManager.AddNewHabit(testHabit);
        assertNotNull(referenceID);
    }

    @Test
    public void testDeleteHabitByTitleTask(){
        final String habitType = "test";
        assertTrue(NetworkDataManager.DeleteHabitByType(habitType));
    }

    @Test
    public void testGetUserHabitsTask(){
        HabitList userHabitsResult = NetworkDataManager.GetUserHabitsById(testGetUserID);
        assertTrue(userHabitsResult.habitListSize() == 2);
    }

    @Test
    public void testGetHabitIDTask(){
        ElasticSearchController.GetHabitIdTask getHabitIdTask = new ElasticSearchController.GetHabitIdTask();
        String habitType = GetHabitTestDataOne.testGetHabitType;
        String habitID = GetHabitTestDataOne.testGetHabitId;
        String retreivedID = null;
        try{
            retreivedID = getHabitIdTask.execute(habitType).get();
        } catch (Exception e){
            assertTrue(false);
        }

        assertTrue(retreivedID.equals(habitID));
    }

    public void testUpdateHabitByIdTask(){
        final String testUpdateHabitTitle = "testAddHabitTitle";
        final String testUpdateHabitReason = "testAddHabitReason";
        final Date testUpdateHabitStartDate = DateHelpers.removeTime(new Date());
        final String[] testUpdateHabitSchedule =  new String[] {"MON", "WED", "FRI", "SUN"};
        final ArrayList<String> testSched = new ArrayList<>(Arrays.asList(testUpdateHabitSchedule));
        final String testUpdateHabitUserID = "AV_2RTVTm9X4YezqHBQM";
        final String testUpdateHabitID = "AWADvALrBOIa5W1F-q6m";
        Habit updatedHabit = new Habit(testUpdateHabitTitle, testUpdateHabitReason, testUpdateHabitStartDate);
        updatedHabit.setScheduledHabitDays(testSched);
        updatedHabit.setAssociatedUserID(testUpdateHabitUserID);
        updatedHabit.setHabitID(testUpdateHabitID);
        assertTrue(NetworkDataManager.UpdateHabit(updatedHabit));
    }

    @Test
    public void testGetHabitByTitleTask() {
        Habit retrievedHabit = NetworkDataManager.GetHabit(GetHabitTestDataOne.testGetHabitType);
        assertNotNull(retrievedHabit.getHabitID());
        assertTrue(retrievedHabit.getHabitID().equals(GetHabitTestDataOne.testGetHabitId));
    }

//    @Test
//    public void testAddHabitEventTask() {
//        HabitEvent testHabitEvent = new HabitEvent(testAddEventTitle, testAddEventHabitType, testAddEventComment);
//        testHabitEvent.setAssociatedUserID("A3x767asdkk312djSDJAKd123");
//        testHabitEvent.setLocation(testAddEventLocation);
//        // testHabitEvent.setPhoto(testAddEventPhoto);
//        String referenceID = NetworkDataManager.AddNewHabitEvent(testHabitEvent);
//        assertNotNull(referenceID);
//    }
}
