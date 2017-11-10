package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.FileOutputStream;

import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.Models.User;

/**
 * Created by hughc on 2017-11-05.
 */

public class LocalDataManager {
    private final String prefFile = "LocalData";
    private String userName;
    private Gson gson;
    private FileOutputStream fos;
    private Context context;

    static private LocalDataManager localDataManager = null;

    //constructor
    public LocalDataManager(Context context, String username){
        this.context = context;
        this.userName = username;
    }

    public static void InitManager(Context context, String username) {
        if (context == null) {
            throw new RuntimeException("Missing context for LocalListManager");
        }
        localDataManager = new LocalDataManager(context, username);
    }

    public static LocalDataManager getManager(){
        if(localDataManager == null){
            throw new RuntimeException("Did not initalize Manager");
        }
        return localDataManager;
    }

<<<<<<< HEAD
    public User loadLocalUserData(HabitList habitList){
        SharedPreferences settings = context.getSharedPreferences(prefFile,Context.MODE_PRIVATE);
        String habitListData = settings.getString(userName,"");
        if (habitListData.equals("")) {
            throw new RuntimeException("no such user exist");
        }else{
            return null; //offlineDataFromString(); //FIRST CONVERT STRING TO JSON, THEN JSON TO OBJECT USING GSON
        }
    }
=======
//    public User loadLocalUserData(HabitList habitList){
//        SharedPreferences settings = context.getSharedPreferences(prefFile,Context.MODE_PRIVATE);
//        String habitListData = settings.getString(userName,"");
//        if (habitListData.equals("")) {
//            throw new RuntimeException("no such user exist");
//        }else{
//            return offlineDataFromString(); //FIRST CONVERT STRING TO JSON, THEN JSON TO OBJECT USING GSON
//        }
//    }
>>>>>>> master

    public void saveHabitList(HabitList habitList){

    }

}
