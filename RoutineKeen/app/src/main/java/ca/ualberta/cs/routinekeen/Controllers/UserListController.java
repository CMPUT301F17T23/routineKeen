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
            // Get the user list from the IOManager

        }

        return userList;
    }

    static public void addUserToList(String username){
        // Create/Retrieve the user from the network storage
        // add a new user to the user list
        User tempUser = new User(username);
        if(NetworkDataManager.GetUser(tempUser) == null) {
            NetworkDataManager.AddNewUser(tempUser);
        }
        getUserList().addUser(tempUser);
    }

    static public void saveUserList() {
        // Use the IOManager to save the user list to shared preferences
        // for the login activity
    }

}
