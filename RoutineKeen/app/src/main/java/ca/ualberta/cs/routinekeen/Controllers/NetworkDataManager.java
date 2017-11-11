package ca.ualberta.cs.routinekeen.Controllers;

import android.util.Log;

import ca.ualberta.cs.routinekeen.Models.User;

/**
 * Created by hdc on 11/6/17.
 * created sperate branch on 11/8/17
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
}
