package ca.ualberta.cs.routinekeen.Views;

import android.content.Context;
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

import com.google.android.gms.common.data.DataBufferObserver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Controllers.NetworkDataManager;
import ca.ualberta.cs.routinekeen.Controllers.UserListController;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.Models.UserList;
import ca.ualberta.cs.routinekeen.R;

public class LoginActivity extends AppCompatActivity{
    private ListView userSelectListView;
    private Button addProfBtn;
    private ArrayList<User> users;
    private ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userSelectListView = (ListView) findViewById(R.id.login_listview_userSelect);
        addProfBtn = (Button) findViewById(R.id.login_btn_addUser);
        initListeners();
    }

    @Override
    protected void onStart(){
        super.onStart();
        //users = UserListController.getUserList().getUsers();
        //adapter = new ArrayAdapter<User>(this, R.layout.login_list_item, users);
        //userSelectListView.setAdapter(adapter);
    }

    private void initListeners() {
        addProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to popup view where user can enter unique profile name
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login_addprofile, null);
                final EditText mProfile = (EditText) mView.findViewById(R.id.login_edit_profileName);
                Button mButton = (Button) mView.findViewById(R.id.login_btn_addProfileAccept);

                mButton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        if(!mProfile.getText().toString().isEmpty()){
                            // Create/Retrieve the user on the network, then add them to the user list,
                            // and save them to the local data storage (shared preferences)
                            String username = mProfile.getText().toString();
                            //NetworkDataManager.AddNewUser(newUser);
                            //User getUser = NetworkDataManager.GetUser(newUser);
                        }
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        userSelectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Navigate to the main menu for the selected user
            }
        });
    }
}
