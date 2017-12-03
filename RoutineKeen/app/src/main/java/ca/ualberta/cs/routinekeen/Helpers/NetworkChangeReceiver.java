package ca.ualberta.cs.routinekeen.Helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Observable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;


/**
 * Created by hughc on 2017-11-30.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkAvailable(context)){
            HabitHistoryController.executeOfflineQueuedRequests();
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
