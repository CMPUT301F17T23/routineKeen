package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Controllers.NetworkDataManager;
import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.R;

public class FollowerHistoryActivity extends AppCompatActivity {

    //private ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();
    private ListView CL;
    private  ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();
    private ArrayAdapter<String> adapter;

    private ArrayList<String> habits = new ArrayList<String>();
    private ArrayList<Integer> countOfEvents = new ArrayList<Integer>();
    private ArrayList<HabitEvent> recentEvent = new ArrayList<HabitEvent>();
    private String clickedEvent;
    private int clickedPosition;
    private ArrayList<String> viewList = new ArrayList<String>();

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



        for(HabitEvent he : habitEvents)
        {
            if(!habits.contains(he.getEventHabitType())) {
                habits.add(he.getEventHabitType());
            }
        }

        for(String i : habits)
        {
            int count = 0;
            for(HabitEvent he : habitEvents)
            {
                if(count == 0)//Save first occurence of event
                {
                    recentEvent.add(he);
                }
                if(i.equals(he.getEventHabitType()))
                {
                    count+=1;
                }

            }
            countOfEvents.add(count);
        }

        //Log.d("tag1", String.valueOf(habits));
        //Log.d("tag1", "Recent: "+String.valueOf(recentEvent));
        /*
        Adds appropriate view to Array to be seen by the user
         */
        for(int i = 0; i < habits.size();i++)
        {
            String view = "Habit Type: " + habits.get(i) + "\nHabit Status: " + countOfEvents.get(i).toString()
                    + " Events Completed \nMost Recent Event:\n     Title: " + recentEvent.get(i).getTitle()
                    +"\n     Comment: " + recentEvent.get(i).getComment() + "\n     Date: " + recentEvent.get(i).getDate().toString();
            viewList.add(view);

        }

        //Set up adapter
        CL = (ListView) findViewById(R.id.habitHistoryList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, viewList);
        CL.setAdapter(adapter);
    }
}
