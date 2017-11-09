package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileOutputStream;

/**
 * Created by hughc on 2017-11-05.
 */

public class LocalDataManager {
    private final String prefFile = "UserList";
    private final String userDataFileLocation = "";
    private Gson gson;
    private FileOutputStream fos;
    private Context context;

    static private LocalDataManager localDataManager = null;

    //constructor
    public LocalDataManager(Context context){this.context = context;}

    public static void InitManager(Context context) {
        if (context == null) {
            throw new RuntimeException("Missing context for LocalListManager");
        }
        localDataManager = new LocalDataManager(context);
    }

    public static LocalDataManager getManager(){
        if(localDataManager == null){
            throw new RuntimeException("Did not initalize Manager");
        }
        return localDataManager;
    }

    

}
