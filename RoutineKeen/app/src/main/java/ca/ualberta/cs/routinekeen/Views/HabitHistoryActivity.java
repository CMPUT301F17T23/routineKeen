package ca.ualberta.cs.routinekeen.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.R;

/*
 - Change android manifest so that main menu is parent and habit history is child, so that when back
  button pressed when on main menu, it will go to main menu (onBackPressed(){//do something})

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
public class HabitHistoryActivity extends AppCompatActivity {

    private ListView CL;
    private ArrayList<HabitEvent> users;
    private ArrayAdapter<HabitEvent> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history);

    }

    /*
    //Load Up User Habit events here
    @Override
    protected void onStart() {
        super.onStart();
        //Load Function
        adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, counters);
        CL.setAdapter(adapter);
    }
    */

    public void addHabitEvent(View view)
    {

    }

    public void filterList(View view)
    {

    }
}