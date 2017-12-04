package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Models.UserList;

/**
 * Created by hughc on 2017-11-05.
 */

public class LocalDataManager {
    private static LocalDataManager localDataManager = null;
    private final String habitListPrefFile = "HabitListFile";
    private final String userListPrefFile = "UserListFile";
    private final String habitHistoryPreFile = "HabitHistoryFile";
    private final String habitTypePrefFile = "TypeFile";
    private final String userListKey = "userList";
    private final String habitListKey = "habitList";
    private final String habitHistoryKey = "habitHistoryList";
    private final String typeListKey = "typeList";
    private Gson gson = new Gson();
    private String jsonString;
    private Context context;
    public LocalDataManager(Context context){
        this.context = context;
    }

    public static void InitManager(Context context) {
        if(localDataManager == null) {
            if (context == null) {
                throw new RuntimeException("No application context provided!");
            }
            localDataManager = new LocalDataManager(context);
        }
    }

    public static LocalDataManager getManager(){
        if(localDataManager == null){
            throw new RuntimeException("Did not initialize Manager");
        }
        return localDataManager;
    }

    public void saveHabitList(HabitList habitList){
        SharedPreferences settings = context.getSharedPreferences(habitListPrefFile,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        jsonString = gson.toJson(habitList.getHabits());
        editor.putString(habitListKey,jsonString);
        editor.apply();
    }

    public HabitList loadHabitList(){
        SharedPreferences settings = context.getSharedPreferences(habitListPrefFile,Context.MODE_PRIVATE);
        String habitListData = settings.getString(habitListKey,"");
        if (habitListData.equals("")) {
            return new HabitList();
        }else{
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            ArrayList<Habit> listOfHabits = gson.fromJson(habitListData, listType);
            return new HabitList(listOfHabits);
        }
    }

    public void saveUserList(UserList userList){
        SharedPreferences settings = context.getSharedPreferences(userListPrefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        jsonString = gson.toJson(userList.getUsers());
        editor.putString(userListKey, jsonString);
        editor.apply();
    }

    public UserList loadUserList(){
        SharedPreferences settings = context.getSharedPreferences(userListPrefFile, Context.MODE_PRIVATE);
        String userListData = settings.getString(userListKey, "");
        if (userListData.equals("")){
            return new UserList();
        } else{
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> listOfUsers = gson.fromJson(userListData, listType);
            return new UserList(listOfUsers);
        }
    }

    public void saveHabitHistory(HabitHistory habitHistory) {
        SharedPreferences settings = context.getSharedPreferences(habitHistoryPreFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        jsonString = gson.toJson(habitHistory.getEvents());
        editor.putString(habitHistoryKey,jsonString);
        editor.apply();
    }

    public HabitHistory loadHabitHistory() {
        //TODO: 11/11/2017  implement
        SharedPreferences settings = context.getSharedPreferences(habitHistoryPreFile,Context.MODE_PRIVATE);
        String habitHistoryData = settings.getString(habitHistoryKey,"");
        if(habitHistoryData == ""){
            return new HabitHistory();
        }else{
            Type listType = new TypeToken<ArrayList<HabitEvent>>(){}.getType();
            ArrayList<HabitEvent> list = gson.fromJson(habitHistoryData,listType);
            return new HabitHistory(list);
        }
    }

    public void saveHabitTypeList(ArrayList<String> typeList){
        SharedPreferences settings = context.getSharedPreferences(habitTypePrefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        jsonString = gson.toJson(typeList);
        editor.putString(typeListKey, jsonString);
        editor.apply();
    }

    public ArrayList<String> loadHabitTypeList() {
        SharedPreferences settings = context.getSharedPreferences(habitTypePrefFile, Context.MODE_PRIVATE);
        String typeListData = settings.getString(typeListKey, "");
        if (typeListData.equals("")){
            return null;
        } else{
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            return gson.fromJson(typeListData, listType);
        }
    }

    public void loadSharedPrefs(String ... prefs) {
        // Helper function to view local data within shared preferences
        // Taken from https://stackoverflow.com/questions/23635644/
        // how-can-i-view-the-shared-preferences-file-using-android-studio
        // Date: Nov. 11, 2017
        // Logging messages left in to view Shared Preferences.
        // I filter out all logs except for ERROR; hence why I am printing error messages.

        Log.i("Loading Shared Prefs", "-----------------------------------");
        Log.i("----------------", "---------------------------------------");

        for (String pref_name: prefs) {
            SharedPreferences preference = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
            for (String key : preference.getAll().keySet()) {
                Log.i(String.format("Shared Preference : %s - %s", pref_name, key),
                        preference.getString(key, "error!"));
            }
            Log.i("----------------", "---------------------------------------");
        }
        Log.i("Finished Shared Prefs", "----------------------------------");
    }

    public void clearUserSharedPrefs(){
        // Clear the user habits that are stored locally
        SharedPreferences habitPref = context.getSharedPreferences(habitListPrefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = habitPref.edit();
        editor.clear();
        editor.commit();

        // Clear the users habit events that are stored locally
        SharedPreferences habitEventPref = context.getSharedPreferences(habitHistoryPreFile, Context.MODE_PRIVATE);
        editor = habitEventPref.edit();
        editor.clear();
        editor.commit();

        // Clear the users habit types thata re stored locally
        SharedPreferences habitTypeEventPref =
                context.getSharedPreferences(habitTypePrefFile, Context.MODE_PRIVATE);
        editor = habitTypeEventPref.edit();
        editor.clear();
        editor.commit();
    }

}
