package ca.ualberta.cs.routinekeen.Controllers;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileOutputStream;

/**
 * Created by hughc on 2017-11-05.
 */

public class LocalDataManager {
    private String userDataFileLocation = "";
    private Gson gson;
    private FileOutputStream fos;
    private Context context;

    public LocalDataManager(Context context){
        this.context = context;
    }
}
