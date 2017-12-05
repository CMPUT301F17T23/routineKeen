package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;
import android.util.Log;

import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Models.UserList;

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

    public static boolean addUser(String username){
        User retrievedUser;
        try{
            retrievedUser = ioManager.getUser(username);
            if(retrievedUser == null){
                String addedUserID = ioManager.addUser(new User(username));
                retrievedUser = new User(username, addedUserID);
            }
        } catch (NetworkUnavailableException e) {
            return false;
        }
        getUserList().addUser(retrievedUser);
        ioManager.saveUserList(getUserList());
        return true;
    }

    public static boolean containsUser(String username){
        return userList.containsUser(username);
    }

    public static boolean removeUser(String username){
        User userToDelete = userList.getUser(username);
        try{
            ioManager.deleteUser(userToDelete.getUserID());
        } catch (NetworkUnavailableException e){
            return false;
        }

        userList.removeUser(username);
        ioManager.saveUserList(userList);
        return true;
    }

    public static void saveUserList(){
        ioManager.saveUserList(getUserList());
    }
}
