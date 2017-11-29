package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.R;

/*

 -Add filter later
 */

/**
 * Shows the user a list of habit events (instances when the user has completed a habit)
 *
 * @author  RoutineKeen
 * @see     HabitEvent
 * @see     ca.ualberta.cs.routinekeen.Models.HabitHistory
 * @version 1.0.0
 */
public class HabitHistoryActivity extends AppCompatActivity implements Observer{
    private ListView CL;
    private final ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();
    private ArrayAdapter<HabitEvent> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        IOManager.initManager(getApplicationContext());
        setContentView(R.layout.activity_habit_history);
        CL = (ListView) findViewById(R.id.habitHistoryList);
        HabitHistoryController.getHabitHistory().addObserver(this);

        /*
        //"Grabs" data on click and transfer it to second activity to be modified or updated.
         */

        CL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(HabitHistoryActivity.this, ViewHabitEvent.class);
                intent.putExtra("View Event", position);
                startActivity(intent);
            }
        });
    }

    protected void onStart(){
        super.onStart();
        habitEvents.clear();
        habitEvents.addAll(HabitHistoryController.getHabitHistory().getEvents());
        adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, habitEvents);
        CL.setAdapter(adapter);
    }

    protected void onDestroy(){
        super.onDestroy();
        HabitHistoryController.saveHabitHistory();
    }


    //Mikee's code, don't have time to debug

    public void addHabitEvent(View view)
    {
        Intent intent = new Intent(this, AddHabitEvent.class);
        startActivity(intent);
    }


    @Override
    public void update(Observable observable, Object o) {
        habitEvents.clear();
        habitEvents.addAll(HabitHistoryController.getHabitHistory().getEvents());
        adapter.notifyDataSetChanged();
    }
}