package ca.ualberta.cs.routinekeen;

import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.util.Log;

import org.junit.Test;

import java.util.Date;

import ca.ualberta.cs.routinekeen.Controllers.ElasticSearchController;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Views.LoginActivity;

import static org.junit.Assert.*;

/**
 * Created by hughc on 2017-11-11.
 */

public class ElasticSearchControllerTest{
    @Test
    public void testGetUserTask(){
        assertTrue(false); // implement later
    }

    @Test
    public void testAddUserTask() throws Throwable{
        final User testUser = new User("test69");
        ElasticSearchController.AddUserTask addUserTask = new ElasticSearchController.AddUserTask();
        User retrievedUser = null;
        try {
            retrievedUser = addUserTask.execute(testUser).get();
        } catch (Exception e){
            //assertTrue(false);
        }
    }

    @Test
    public void testAddHabitTask(){
        assertTrue(false); // implement later
    }

    @Test
    public void testGetHabitByTitleTask() {
        assertTrue(false); // implement later
    }

    @Test
    public void testAddHabitEventTask() {
        HabitEvent habitEvent = new HabitEvent("chillen", "walking dog", "swag out dudez");
        habitEvent.setHabitEventUserID("A3x767asdkk312djSDJAKd123");

        ElasticSearchController.AddHabitEventTask addHabitEventTask = new ElasticSearchController.AddHabitEventTask();
        addHabitEventTask.execute(habitEvent);
    }
}
