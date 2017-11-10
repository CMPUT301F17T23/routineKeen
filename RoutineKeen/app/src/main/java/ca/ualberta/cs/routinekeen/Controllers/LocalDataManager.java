package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.FileOutputStream;

import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Models.UserList;

/**
 * Created by hughc on 2017-11-05.
 */

public class LocalDataManager {
    private final String userListPrefFile = "UserListFile";
    private final String prefFile = "LocalData";
    private String userName;
    private Gson gson;
    private FileOutputStream fos;
    private Context context;

    static private LocalDataManager localDataManager = null;

    //constructor
    public LocalDataManager(Context context){
        this.context = context;
    }

    public static void InitManager(Context context) {
        if(localDataManager == null) {
            if (context == null) {
                throw new RuntimeException("Missing context for LocalListManager");
            }
            localDataManager = new LocalDataManager(context);
        }
    }

    public static LocalDataManager getManager(){
        if(localDataManager == null){
            throw new RuntimeException("Did not initalize Manager");
        }
        return localDataManager;
    }

    public UserList loadUserList(){
        return null;
    }

    public User loadLocalUserData(HabitList habitList){
        SharedPreferences settings = context.getSharedPreferences(prefFile,Context.MODE_PRIVATE);
        String habitListData = settings.getString(userName,"");
        if (habitListData.equals("")) {
            throw new RuntimeException("no such user exist");
        }else{
            return null; //offlineDataFromString(); //FIRST CONVERT STRING TO JSON, THEN JSON TO OBJECT USING GSON
        }
    }

    public void saveHabitList(HabitList habitList){

    }

}
