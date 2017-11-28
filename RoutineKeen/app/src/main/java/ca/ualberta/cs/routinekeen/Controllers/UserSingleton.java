package ca.ualberta.cs.routinekeen.Controllers;

import ca.ualberta.cs.routinekeen.Helpers.UserDataStatus;
import ca.ualberta.cs.routinekeen.Models.User;

public class UserSingleton {
    private static User currentUser = null;
    private static UserDataStatus userHabitData = null;
    private static UserDataStatus userHabitEventData = null;

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

