package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.R;

/**
 * Created by tiakindele on 2017-11-07.
 */

public class HabitListActivity extends AppCompatActivity {

    ImageButton addHabitBtn;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_list);
        HabitList hL = new HabitList();
        ArrayList<Habit> habitList = hL.getHabitList();
        // DEBUG TOOL
        // Log.d("myTag", String.valueOf(habitList));
        lv = (ListView) findViewById(R.id.listOfUserHabits);
        ArrayAdapter<Habit> habitArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, habitList);
        lv.setAdapter(habitArrayAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addHabitBtn = (ImageButton) findViewById(R.id.addNewHabit);

        addHabitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitListActivity.this,
                        NewHabitActivity.class);
                startActivity(intent);
            }
        });
    }
}
