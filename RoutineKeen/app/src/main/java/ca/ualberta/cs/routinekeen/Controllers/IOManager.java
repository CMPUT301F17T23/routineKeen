package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Models.UserList;

/**
 * A proxy class used by all the other controllers for each activity to do IO
 * operations with the local or network storage. Provides a central class for doing
 * all data CRUD operations within the application.
 *
 * @author Hugh Craig
 * @see LocalDataManager
 * @see NetworkDataManager
 * @version 1.0.0
 */
public class IOManager {
    private static IOManager ioManager = null;
    private LocalDataManager localDM;
    private NetworkDataManager networkDm;
    private Context context;

    public IOManager(Context context) {
        this.context = context;
        LocalDataManager.InitManager(context);
        localDM = LocalDataManager.getManager();
    }

    public static void initManager(Context context) {
        if (context == null) {
            throw new RuntimeException("Missing Context");
        }
        ioManager = new IOManager(context);
    }

    public static IOManager getManager() {
        if (ioManager == null) {
            throw new RuntimeException("Did not initialize IOManager");
        }
        return ioManager;
    }

    public UserList loadUserList() {
        return localDM.loadUserList();
    }

    public void saveUserList(UserList userList) {
        localDM.saveUserList(userList);
    }

    public HabitList loadUserHabitList(String userId) throws NetworkUnavailableException{
        HabitList retrievedHabits;
        if(isNetworkAvailable()){
            retrievedHabits = NetworkDataManager.GetUserHabitsById(userId);
        } else {
            throw new NetworkUnavailableException();
        }

        return retrievedHabits;
    }

    public void saveHabitList(HabitList habitList) {
        localDM.saveHabitList(habitList);
    }

    public HabitHistory loadUserHabitHistory(String userID) {
        HabitHistory retrievedHabitEvents;
        if (isNetworkAvailable()){
            retrievedHabitEvents = NetworkDataManager.GetUserHabitEvents(userID);
        } else{
            retrievedHabitEvents = localDM.loadHabitHistory();
        }

        return retrievedHabitEvents;
    }

    public void saveHabitHistory(HabitHistory habitHistory) {
        localDM.saveHabitHistory(habitHistory);
    }

    public ArrayList<String> loadUserHabitTypes(String userID){
        ArrayList<String> typeList = null;
        if(isNetworkAvailable()){
            typeList = NetworkDataManager.GetUserHabitTypes(userID);
            localDM.saveHabitTypeList(typeList);
        } else {
            typeList = localDM.loadHabitTypeList();
        }

        return typeList;
    }

    public void saveUserHabitTypes(ArrayList<String> typeList){
        localDM.saveHabitTypeList(typeList);
    }

    public String addUser(User user) throws NetworkUnavailableException{
        String userID = null;
        if (isNetworkAvailable()) {
            userID = NetworkDataManager.AddNewUser(user);
        } else{
            throw new NetworkUnavailableException();
        }

        return userID;
    }

    public String addHabit(Habit habit) throws NetworkUnavailableException{
        if(isNetworkAvailable()){
            return NetworkDataManager.AddNewHabit(habit);
        } else{
            throw new NetworkUnavailableException();
        }
    }

    public String addHabitEvent(HabitEvent event) throws NetworkUnavailableException{
        if(isNetworkAvailable()){
            return NetworkDataManager.AddNewHabitEvent(event);
        } else {
            throw new NetworkUnavailableException();
        }
    }

    public User getUser(String username) throws NetworkUnavailableException{
        User retrievedUser;
        if(isNetworkAvailable()){
            retrievedUser = NetworkDataManager.GetUser(username);
        } else {
            throw new NetworkUnavailableException();
        }
        
        return retrievedUser;
    }

    public void deleteHabit(String habitID) throws NetworkUnavailableException{
        if(isNetworkAvailable()){
            NetworkDataManager.DeleteHabit(habitID);
        } else{
            throw new NetworkUnavailableException();
        }
    }

    public void deleteHabitEvent(String eventID) throws NetworkUnavailableException {
        if (isNetworkAvailable()){
            NetworkDataManager.DeleteHabitEvent(eventID);
        } else{
            throw new NetworkUnavailableException();
        }
    }

    public void updateHabit(Habit habit) throws NetworkUnavailableException {
        if(isNetworkAvailable()){
            NetworkDataManager.UpdateHabit(habit);
        } else {
            throw new NetworkUnavailableException();
        }
    }

    public void updateHabitEvent(HabitEvent event) throws NetworkUnavailableException{
        if(isNetworkAvailable()){
            NetworkDataManager.UpdateHabitEvent(event);
        } else {
            throw new NetworkUnavailableException();
        }
    }

    public void sendFollowerRequest(User follower, User followee) throws NetworkUnavailableException{

        // Add the user issuing the request to the requested users
        // follower request list.
        ArrayList<String> usersFollowerRequests = followee.getFollowerRequests();
        if(usersFollowerRequests == null)
            usersFollowerRequests = new ArrayList<>();
        usersFollowerRequests.add(follower.getUsername());
        followee.setFollowerRequests(usersFollowerRequests);

        // Update the followee's follower request list
        if(isNetworkAvailable()){
            NetworkDataManager.UpdateUser(followee);
        } else{
            throw new NetworkUnavailableException();
        }
    }

    public void respondToFollowerRequest(User follower, User followee, boolean choice) throws NetworkUnavailableException {
        // Take the follower off the followee's request list
        // since they have now accepted it
        ArrayList<String> usersFollowerRequests = followee.getFollowerRequests();
        usersFollowerRequests.remove(follower.getUsername());
        followee.setFollowerRequests(usersFollowerRequests);

        ArrayList<String> usersFollowerList = follower.getFollowerList();

        // followee accepts the follower request
        if (choice == true) {
            // Put the followee on the followers follow list
            usersFollowerList.add(followee.getUsername());
            follower.setFollowerList(usersFollowerList);
        }

        if (isNetworkAvailable()) {
            NetworkDataManager.UpdateUser(followee);
            NetworkDataManager.UpdateUser(follower);
        } else {
            throw new NetworkUnavailableException();
        }
    }

    public void loadSharedPrefs(String... prefs){
        localDM.loadSharedPrefs(prefs);
    }

    public void clearUserSharedPrefs() {
        localDM.clearUserSharedPrefs();
    }

    private boolean isNetworkAvailable() {
        // Taken from: https://stackoverflow.com/questions/4238921/
        // detect-whether-there-is-an-internet-connection-available-on-android
        // Date Taken: Nov 21, 2017
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}