package ca.ualberta.cs.routinekeen.Controllers;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;

/**
 * Created by Mikee V on 2017-12-01.
 */

//Gets data. uses a string to limit the users return array
public class FindFollowersController {
    private static IOManager ioManager = IOManager.getManager();

    public static User getUsers(String userToSearch)
    {
        User user = null;
        try{
            user = ioManager.getUser(userToSearch);
        }
        catch(NetworkUnavailableException e){

        }
        return user;
    }

    public static void addToRequestList(User userRequesting, User requestedUser)
    {
        try{
            ioManager.sendFollowerRequest(userRequesting, requestedUser);
        }
        catch (NetworkUnavailableException e){

        }
    }
    public static ArrayList<String> getFollowerRequests(User user)
    {
        return user.getFollowerRequests();
    }

    public static ArrayList<String> getFeedList(User user)
    {
        return user.getFollowerList();
    }
    public static HabitHistory getHabitHistory(User user)
    {
        //HabitHistory habitHistory;
        return ioManager.loadUserHabitHistory(user.getUserID());

        //return habitHistory;
    }
}
