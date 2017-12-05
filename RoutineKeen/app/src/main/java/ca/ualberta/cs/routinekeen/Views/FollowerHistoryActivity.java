package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Controllers.NetworkDataManager;
import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.R;

public class FollowerHistoryActivity extends AppCompatActivity {

    //private ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();
    private ListView CL;
    private  ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();
    private ArrayAdapter<HabitEvent> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_history);

        Intent intent = getIntent();
        String user = intent.getStringExtra("Follower Name");

        try{
            habitEvents = (ArrayList<HabitEvent>) NetworkDataManager.GetUserHabitEvents(IOManager
                    .getManager().getUser(user).getUserID()).getEvents();
        }catch(NetworkUnavailableException e) {

        }
        CL = (ListView) findViewById(R.id.habitHistoryList);
        adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, habitEvents);
        CL.setAdapter(adapter);


    }
}
