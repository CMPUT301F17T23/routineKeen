package ca.ualberta.cs.routinekeen.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.R;

public class HabitScheduleActivity extends AppCompatActivity {

    private ListView lv;
    private final ArrayList<Habit> scheduledHabitList = new ArrayList<Habit>();
    private ArrayAdapter<Habit> habitArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("Schedule-onC", "flag1");
        setContentView(R.layout.activity_habit_schedule);

        lv = (ListView) findViewById(R.id.habitSchedule_listView);
        Log.d("Schedule-onC", "flag2");
        //initListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            scheduledHabitList.addAll(HabitListController.getTodaysHabits().getHabits());
        } catch (Exception e) {
            e.printStackTrace();
        }
        habitArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, scheduledHabitList);
        lv.setAdapter(habitArrayAdapter);
    }

    private void initListeners() {
        // Will be implemented if we opt to allow editing of habits through the habit schedule
    }
}
