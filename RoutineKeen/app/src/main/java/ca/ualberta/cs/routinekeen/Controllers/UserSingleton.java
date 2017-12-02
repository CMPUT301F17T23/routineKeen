package ca.ualberta.cs.routinekeen.Controllers;

import ca.ualberta.cs.routinekeen.Helpers.MarkedForStatus;
import ca.ualberta.cs.routinekeen.Models.User;

public class UserSingleton {
    private static User currentUser = null;

    private UserSingleton(){}

    public static void setCurrentUser(User user){
        currentUser = user;
    }

    public static User getCurrentUser(){
        if ( currentUser == null ){
            throw new RuntimeException("Current user singleton not instantiated");
        }else{
            return currentUser;
        }
    }
}

