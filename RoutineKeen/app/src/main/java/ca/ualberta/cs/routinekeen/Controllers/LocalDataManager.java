package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Models.UserList;

/**
 * Created by hughc on 2017-11-05.
 */

public class LocalDataManager {
    private final String habitListPrefFile = "HabitList";
    private final String userListPrefFile = "UserListFile";
    private final String userListKey = "userList";
    private final String HabitListKey = "HabitList";

    private Gson gson =  new Gson();
    String jsonString;
    private Context context;

    static private LocalDataManager localDataManager = null;

    //constructor
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
        jsonString = gson.toJson(habitList);
        editor.putString(HabitListKey,jsonString);
        editor.apply();
    }

    public UserList loadUserList(){
        SharedPreferences settings = context.getSharedPreferences(userListPrefFile, Context.MODE_PRIVATE);
        String userListData = settings.getString(userListKey, "");
        if (userListData.equals("")){
            return new UserList();
        } else{
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            return gson.fromJson(userListData, listType);
        }
    }

    public void saveUserList(UserList userList){
        SharedPreferences settings = context.getSharedPreferences(userListPrefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        jsonString = gson.toJson(userList);
        editor.putString(userListKey, jsonString);
        editor.apply();
    }

    public HabitList loadHabitList(){
        SharedPreferences settings = context.getSharedPreferences(habitListPrefFile,Context.MODE_PRIVATE);
        String habitListData = settings.getString(HabitListKey,"");
    if (habitListData.equals("")) {
            return new HabitList();
        }else{
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            return( gson.fromJson(habitListData,listType) );
        }
    }
}
