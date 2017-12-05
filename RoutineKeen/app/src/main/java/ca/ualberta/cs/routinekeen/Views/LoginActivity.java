package ca.ualberta.cs.routinekeen.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Network;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferObserver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Controllers.LocalDataManager;
import ca.ualberta.cs.routinekeen.Controllers.UserListController;
import ca.ualberta.cs.routinekeen.Controllers.UserSingleton;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.R;

/**
 * Gives the user a list of existing profiles to choose from and the option to create a new profile
 *
 * @author  RoutineKeen
 * @see     UserMenu
 * @version 1.0.0
 */

public class LoginActivity extends AppCompatActivity implements Observer{
    private ListView userSelectListView;
    private Button addProfBtn;
    private Button deleteProfBtn;
    private final ArrayList<User> users = new ArrayList<User>();
    private ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IOManager.initManager(getApplicationContext());
        setContentView(R.layout.activity_login);
        userSelectListView = (ListView) findViewById(R.id.login_listview_userSelect);
        addProfBtn = (Button) findViewById(R.id.login_btn_addUser);
        deleteProfBtn = (Button) findViewById(R.id.login_btn_deleteUser);
        UserListController.getUserList().addObserver(this);
        initListeners();
    }

    @Override
    protected void onStart(){
        super.onStart();
        users.clear();
        users.addAll(UserListController.getUserList().getUsers());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        userSelectListView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        UserListController.saveUserList();
    }

    @Override
    public void update(Observable observable, Object o) {
        users.clear();
        users.addAll((ArrayList<User>) o);
        adapter.notifyDataSetChanged();
    }

    private void initListeners() {
        addProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to popup view where user can enter unique profile name
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login_addprofile, null);
                mBuilder.setView(mView);

                final EditText mProfile = (EditText) mView.findViewById(R.id.login_edit_profileName);
                Button mButton = (Button) mView.findViewById(R.id.login_btn_addProfileAccept);

                final AlertDialog dialog = mBuilder.create();
                mButton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        if(!mProfile.getText().toString().isEmpty()){
                            // Create/Retrieve the user on the network, then add them to the user list,
                            // and save them to the local data storage (shared preferences)
                            String username = mProfile.getText().toString().trim();
                            if(UserListController.addUser(username)) {
                                dialog.dismiss();
                            } else{
                                Toast.makeText(LoginActivity.this, "Failed to retrieve/add user. Please make sure " +
                                        "you are connected to a network.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                dialog.show();
            }
        });

        deleteProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login_deleteprofile, null);
                mBuilder.setView(mView);

                final EditText mProfile = (EditText) mView.findViewById(R.id.login_edit_deleteProfName);
                Button mButtonYes = (Button) mView.findViewById(R.id.login_btn_acceptDelete);
                Button mButtonNo = (Button) mView.findViewById(R.id.login_btn_declineDelete);

                final AlertDialog dialog = mBuilder.create();
                mButtonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!mProfile.getText().toString().isEmpty()){
                            String profName = mProfile.getText().toString().trim();
                            if(UserListController.containsUser(profName)) {
                                if(UserListController.removeUser(profName)){
                                    Toast.makeText(LoginActivity.this, "Profile successfully deleted.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else{
                                    Toast.makeText(LoginActivity.this, "Profile failed to delete. Please" +
                                            " check your connection and try again.", Toast.LENGTH_LONG).show();
                                }
                            } else{
                                Toast.makeText(LoginActivity.this, "User profile does not exist in" +
                                        " your user list. Please try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                mButtonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        userSelectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Navigate to the main menu for the selected user
                UserSingleton.setCurrentUser(users.get(i));
                HabitHistoryController.clearController();
                HabitListController.clearController();
                Intent intent = new Intent(LoginActivity.this, UserMenu.class);
                startActivity(intent);
            }
        });
    }
}
