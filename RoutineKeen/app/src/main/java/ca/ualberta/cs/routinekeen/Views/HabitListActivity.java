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
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.R;

/**
 * Created by tiakindele on 2017-11-07.
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
        lv = (ListView) findViewById(R.id.listOfUserHabits);
        addHabitBtn = (ImageButton) findViewById(R.id.addNewHabit);
        HabitListController.getHabitList().addObserver(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HabitListController.saveHabitList();
    }

    @Override
    public void update(Observable observable, Object o) {
        habitList.clear();
        habitList.addAll((ArrayList<Habit>) o);
        habitArrayAdapter.notifyDataSetChanged();
    }

    public void initListeners(){
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
                Intent intent = new Intent(HabitListActivity.this, HabitDetailsActivity.class);
                Habit selectedHabit = (Habit) adapterView.getItemAtPosition(pos);

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                intent.putExtra("title", selectedHabit.getHabitTitle());
                intent.putExtra("reason", selectedHabit.getHabitReason());
                intent.putExtra("startDate", sdf.format(selectedHabit.getStartDate()));
                intent.putExtra("position", pos);
                startActivity(intent);
            }
        });
    }
}
