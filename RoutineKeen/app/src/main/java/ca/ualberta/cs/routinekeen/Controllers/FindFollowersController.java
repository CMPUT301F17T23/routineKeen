package ca.ualberta.cs.routinekeen.Controllers;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;

/**A class used to communicate with the network, particularly elastic search server, for the application,
 * specifically the HabitFollowingSharingActivity, methods in this controller class pass parameters in models
 * and/or the network to grab/execute data asynchronously.
 *
 *
 * @Author Mikee Villanueva
 * @see IOManager
 * @see ca.ualberta.cs.routinekeen.Views.HabitFollowingSharing
 * @version 1.1.0
 */

//Gets data. uses a string to limit the users return array
public class FindFollowersController {
    private static IOManager ioManager = IOManager.getManager();

    /**
     *
     * @param userToSearch
     * @return
     */
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
