package ca.ualberta.cs.routinekeen.Controllers;

import android.util.Log;

import ca.ualberta.cs.routinekeen.Models.Habit;
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
    public static User AddNewUser(User user){
        ElasticSearchController.AddUserTask addUserTask = new ElasticSearchController.AddUserTask();
        User addedUser = null;
        try{
            addedUser = addUserTask.execute(user).get();
        } catch(Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return addedUser;
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

    public static Habit AddHabit(Habit habit){
        ElasticSearchController.AddHabitTask addHabitTask = new ElasticSearchController.AddHabitTask();
        Habit addedHabit = null;
        try{
            addedHabit = addHabitTask.execute(habit).get();
        } catch (Exception e) {
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return addedHabit;
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
}
