package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.R;

/**
 * Presents the user with a list of all their habits, with the option to add a new habit, or select
 * an existing one to edit and view more details
 *
 * @author  RoutineKeen
 * @see     HabitEditActivity
 * @version 1.0.0
 */

public class HabitListActivity extends AppCompatActivity implements Observer{
    private ImageButton addHabitBtn;
    private ListView lv;
    private final ArrayList<Habit> habitList = new ArrayList<Habit>();
    private ArrayAdapter<Habit> habitArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        IOManager.initManager(getApplicationContext());
        lv = (ListView) findViewById(R.id.listOfUserHabits);
        addHabitBtn = (ImageButton) findViewById(R.id.addNewHabit);
        try {
            HabitListController.initHabitList();
            HabitListController.getHabitList().addObserver(this);
        } catch (NetworkUnavailableException e){
            Toast.makeText(this, "You must be connected to a network" +
                    " to view, edit, and add habits.", Toast.LENGTH_LONG).show();
            finish();
        }
        initListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        habitList.clear();
        habitList.addAll(HabitListController.getHabitList().getHabits());
        habitArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, habitList);
        lv.setAdapter(habitArrayAdapter);
    }

    /**
     * Updates adds new habit to habitList and updates ListView
     * @param observable
     * @param o             Habit to be added
     */
    @Override
    public void update(Observable observable, Object o) {
        habitList.clear();
        habitList.addAll((ArrayList<Habit>) o);
        habitArrayAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Habit list has been updated.", Toast.LENGTH_SHORT).show();
    }

    private void initListeners(){
        addHabitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitListActivity.this,
                        NewHabitActivity.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intent intent = new Intent(HabitListActivity.this, HabitEditActivity.class);
                Habit selectedHabit = (Habit) adapterView.getItemAtPosition(pos);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                intent.putExtra("title", selectedHabit.getHabitTitle());
                intent.putExtra("reason", selectedHabit.getHabitReason());
                intent.putExtra("startDate", sdf.format(selectedHabit.getStartDate()));
                intent.putStringArrayListExtra("scheduledDays", selectedHabit.getScheduledHabitDays());
                intent.putExtra("position", pos);
                startActivity(intent);
            }
        });
    }
}
