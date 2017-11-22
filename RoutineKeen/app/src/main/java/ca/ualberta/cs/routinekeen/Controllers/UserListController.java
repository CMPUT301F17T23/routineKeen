package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;
import android.util.Log;

import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
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
        // make UserListController un-instantiable
    }

    public static UserList getUserList(){
        if (userList == null){
            // Get the user list from local data manger (stored in shared pref)
            userList = ioManager.loadUserList();
        }
        return userList;
    }

    public static boolean addUserToList(String username){
        User retrievedUser;
        try{
            retrievedUser = ioManager.getUser(username);
            if(retrievedUser == null){
                retrievedUser = NetworkDataManager.AddNewUser(new User(username));
            }
        } catch (NetworkUnavailableException e) {
            Log.d("Retrieving/Adding User", e.getMessage());
            return false;
        }
        getUserList().addUser(retrievedUser);
        ioManager.saveUserList(getUserList());
        return true;
    }

    public static boolean saveUserList(){
        ioManager.saveUserList(getUserList());
        return true;
    }
}
