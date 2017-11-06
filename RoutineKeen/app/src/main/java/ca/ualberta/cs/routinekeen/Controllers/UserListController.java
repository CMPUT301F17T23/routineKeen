package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;

import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Models.UserList;

/**
 * Created by hughc on 2017-11-05.
 */

public class UserListController {
    private static UserList userList = null;
    private Context context;

    static public UserList getUserList(){
        if (userList == null){
            // Get the user list from local data manger (stored in shared pref)

            // Add an observer to the controller that saves the user list
            // using the local data manager when the user list model is updated
        }

        return userList;
    }

    static public void addUserToList(User user){
        // add a new user to the user list
        userList.addUser(user);
    }

    static public void saveUserList() {
        // Use the local data manager to save the user list to shared preferences
    }

}
