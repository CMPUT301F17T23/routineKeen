package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.R;

/**
 * Created by tiakindele on 2017-11-07.
 */

public class HabitListActivity extends AppCompatActivity {

    ImageButton addHabitBtn;
    private ListView lv;
    private ArrayAdapter<Habit> habitArrayAdapter;

    private static final int HABIT_DETAILS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_list);
        // DEBUG TOOL
        // Log.d("myTag", String.valueOf(habitList));
        lv = (ListView) findViewById(R.id.listOfUserHabits);
        Collection<Habit> habits = HabitListController.getHabitList().getHabitList();
        final ArrayList<Habit> habitList = new ArrayList<Habit>(habits);
        habitArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, habitList);
        lv.setAdapter(habitArrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intent intent = new Intent(HabitListActivity.this, HabitDetailsActivity.class);
                Habit selectedHabit = (Habit) adapterView.getItemAtPosition(pos);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                intent.putExtra("title", selectedHabit.getHabitTitle());
                intent.putExtra("reason", selectedHabit.getHabitReason());
                intent.putExtra("startDate", sdf.format(selectedHabit.getStartDate()));
                intent.putExtra("position", pos);

//                String data =(String)adapterView.getItemAtPosition(pos);
//                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
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
                startActivityForResult(intent, HABIT_DETAILS_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == HABIT_DETAILS_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bundle data = intent.getExtras();
                int pos = data.getInt("position");

                if (data.getBoolean("habitEdited")) {
                    Habit selectedHabit = (Habit) lv.getItemAtPosition(pos);
                    selectedHabit.setHabitTitle(data.getString("title"));
                    selectedHabit.setHabitReason(data.getString("reason"));
                    habitArrayAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
