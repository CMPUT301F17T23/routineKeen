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
    private static IOManager ioManager = IOManager.getManager();

    private UserListController(){
        // SIMULATE STATIC CLASS / HIDE CONSTRUCTOR
    }

    public static UserList getUserList(){
        if (userList == null){
            // Get the user list from local data manger (stored in shared pref)
            userList = ioManager.loadUserList();
            // Add an observer to the controller that saves the user list
            // using the local data manager when the user list model is updated
        }

        return userList;
    }

    static public void addUserToList(User user){
        // Create/Retrieve the user from the network storage
        // add a new user to the user list

        getUserList().addUser(user);
    }

    static public void saveUserList() {
        // Use the local data manager to save the user list to shared preferences
    }

}
