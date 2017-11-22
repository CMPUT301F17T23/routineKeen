package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
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

    public User addUser(User user) throws NetworkUnavailableException{
        User retrievedUser;
        if (isNetworkAvailable()) {
            retrievedUser = NetworkDataManager.AddNewUser(user);
        } else{
            throw new NetworkUnavailableException();
        }

        return retrievedUser;
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

    public void saveUserList(UserList userList) {
        localDM.saveUserList(userList);
    }

    public void saveHabitList(HabitList habitList) {
        localDM.saveHabitList(habitList);
    }

    public HabitList loadHabitList() {
        return ( localDM.loadHabitList() );
    }

    public HabitHistory loadHabitHistory() {
        return localDM.loadHabitHistory();
    }

    public void saveHabitHistory(HabitHistory habitHistory) {
        localDM.saveHabitHistory(habitHistory);
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