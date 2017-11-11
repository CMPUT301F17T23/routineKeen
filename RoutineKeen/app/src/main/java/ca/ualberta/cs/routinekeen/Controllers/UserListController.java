package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;

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

    static public void addUserToList(String username){
        User tempUser = new User(username);
        if(NetworkDataManager.GetUser(username) == null) {
            NetworkDataManager.AddNewUser(tempUser);
        }
        tempUser = NetworkDataManager.GetUser(username);
        getUserList().addUser(tempUser);
        ioManager.saveUserList(getUserList());
    }
}
