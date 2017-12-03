package ca.ualberta.cs.routinekeen.Controllers;

import android.util.Log;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.Models.User;

/**
 * A class used to communicate with the network storage for
 * the application. Most of the methods within this class simply
 * pass on their parameters to the elastic search controller to execute
 * the request asynchronously.
 *
 * @author Hugh Craig
 * @see ElasticSearchController
 * @see IOManager
 * @version 1.0.0
 */

public class NetworkDataManager {
    public static String AddNewUser(User user){
        ElasticSearchController.AddUserTask addUserTask = new ElasticSearchController.AddUserTask();
        String referenceID = null;
        try{
            referenceID = addUserTask.execute(user).get();
        } catch(Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return referenceID;
    }

    public static User GetUser(String username){
        ElasticSearchController.GetUserTask getUserTask = new ElasticSearchController.GetUserTask();
        User retrievedUser = null;
        try{
            retrievedUser = getUserTask.execute(username).get();
        } catch(Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return retrievedUser;
    }

    public static String AddNewHabit(Habit habit){
        ElasticSearchController.AddHabitTask addHabitTask = new ElasticSearchController.AddHabitTask();
        String referenceID = null;
        try{
            referenceID = addHabitTask.execute(habit).get();
        } catch (Exception e) {
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return referenceID;
    }

    public static boolean UpdateUser(User user){
        ElasticSearchController.UpdateUserTask updateUserTask =
                new ElasticSearchController.UpdateUserTask();
        Boolean result = null;
        try{
            result = updateUserTask.execute(user).get();
        } catch(Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return result.booleanValue();
    }

    public static boolean DeleteHabit(String habitID){
        ElasticSearchController.DeleteHabitTask deleteHabitByTitleTask = new ElasticSearchController.DeleteHabitTask();
        Boolean result = null;
        try{
            result = deleteHabitByTitleTask.execute(habitID).get();
        } catch(Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return result.booleanValue();
    }

    public static boolean UpdateHabit(Habit habit){
        ElasticSearchController.UpdateHabitTask updateHabitByIDTask =
                new ElasticSearchController.UpdateHabitTask();
        Boolean result = null;
        try{
            result = updateHabitByIDTask.execute(habit).get();
        } catch(Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return result.booleanValue();
    }

    public static HabitList GetUserHabitsById(String userId){
        ElasticSearchController.GetUserHabitsTask getUserHabitsTask = new ElasticSearchController.GetUserHabitsTask();
        ArrayList<Habit> userHabits = null;
        try{
            userHabits = getUserHabitsTask.execute(userId).get();
        } catch (Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return new HabitList(userHabits);
    }

    public static Habit GetHabit(String habitType){
        ElasticSearchController.GetHabitByTitleTask getHabitTask = new ElasticSearchController.GetHabitByTitleTask();
        Habit retrievedHabit = null;
        try{
            retrievedHabit = getHabitTask.execute(habitType).get();
        } catch (Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return retrievedHabit;
    }

    public static String AddNewHabitEvent(HabitEvent event){
        ElasticSearchController.AddHabitEventTask addHabitEventTask = new ElasticSearchController.AddHabitEventTask();
        String referenceID = null;
        try{
            referenceID = addHabitEventTask.execute(event).get();
        } catch (Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return referenceID;
    }

    public static boolean UpdateHabitEvent(HabitEvent habitEvent){
        ElasticSearchController.UpdateHabitEventTask updateHabitEventTask =
                new ElasticSearchController.UpdateHabitEventTask();

        Boolean result = null;
        try{
            result = updateHabitEventTask.execute(habitEvent).get();
        } catch (Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return result.booleanValue();
    }

    public static boolean DeleteHabitEvent(String habitEventID){
        ElasticSearchController.DeleteHabitEventTask deleteHabitEventTask =
                new ElasticSearchController.DeleteHabitEventTask();
        Boolean result = null;
        try{
            result = deleteHabitEventTask.execute(habitEventID).get();
        } catch(Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return result.booleanValue();
    }

    public static HabitHistory GetUserHabitEvents(String userID){
        ElasticSearchController.GetUserHabitEventsTask getUserHabitEventsTask = new ElasticSearchController.GetUserHabitEventsTask();
        ArrayList<HabitEvent> retrievedHabitEvents = null;
        try{
            retrievedHabitEvents = getUserHabitEventsTask.execute(userID).get();
        } catch (Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return new HabitHistory(retrievedHabitEvents);
    }
}
