package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;
import android.util.Log;

import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Models.UserList;

/**
 * Created by hughc on 2017-11-05.
 */

public class UserListController {
    private static UserList userList = null;
    private static Context context;
    private static IOManager ioManager = IOManager.getManager();

    private UserListController(){
        // SIMULATE STATIC CLASS / HIDE CONSTRUCTOR
    }

    public static UserList getUserList(){
        if (userList == null){
            // Get the user list from local data manger (stored in shared pref)
            userList = ioManager.loadUserList();
        }
        return userList;
    }

    public static void addUserToList(String username){
        User retrievedUser = NetworkDataManager.GetUser(username);
        if(retrievedUser == null) {
            retrievedUser = NetworkDataManager.AddNewUser(new User(username));
        }
        getUserList().addUser(retrievedUser);
        ioManager.saveUserList(getUserList());
    }

    public static void saveUserList(){
        ioManager.saveUserList(getUserList());
    }
}
