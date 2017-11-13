package ca.ualberta.cs.routinekeen.Controllers;

import ca.ualberta.cs.routinekeen.Models.User;

/**
 * Created by Saddog on 11/10/2017.
 */

public class UserSingleton {
    private UserSingleton(){}
    private static User currentUser = null;
    public static void setCurrentUser(User user){currentUser = user;}
    public static User getCurrentUser(){
        if ( currentUser == null ){
            throw new RuntimeException("Current user singleton not instantiated");
        }else{
            return currentUser;
        }
    }
}

