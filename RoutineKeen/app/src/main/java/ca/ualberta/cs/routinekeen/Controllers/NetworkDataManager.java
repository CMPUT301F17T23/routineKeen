package ca.ualberta.cs.routinekeen.Controllers;

import android.util.Log;

import ca.ualberta.cs.routinekeen.Models.User;

/**
 * Created by hdc on 11/6/17.
 */

public class NetworkDataManager {
    public static void AddNewUser(User user){
        ElasticSearchController.AddUserTask addUserTask = new ElasticSearchController.AddUserTask();
        addUserTask.execute(user);
    }

    public static User GetUser(User user){
        ElasticSearchController.GetUserTask getUserTask = new ElasticSearchController.GetUserTask();
        User retrievedUser = null;
        try{
            retrievedUser = getUserTask.execute(user.getUsername()).get();
        } catch(Exception e){
            Log.i("Error", "SOMETHING WENT WRONG WITH ELASTIC SEARCH MOFO!");
        }

        return retrievedUser;
    }
}
