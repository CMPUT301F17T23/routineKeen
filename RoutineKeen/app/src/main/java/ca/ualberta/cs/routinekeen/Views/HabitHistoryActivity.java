package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private Integer FILTER_REQUEST = 1;
    private static final int FILTER_BY_TYPE = 1;
    private static final int FILTER_BY_COMMENT = 2;
    private String filter;
    private ListView CL;
    private final ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();
    private ArrayAdapter<HabitEvent> adapter;
    private Button clearFilterButton;
    private TextView filterFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        IOManager.initManager(getApplicationContext());
        setContentView(R.layout.activity_habit_history);
        CL = (ListView) findViewById(R.id.habitHistoryList);
        filterFlag = (TextView) findViewById(R.id.filterFlagTextView);
        clearFilterButton = (Button) findViewById(R.id.clearFilterButton);
        Button filterButton = (Button) findViewById(R.id.filterButton);
        HabitHistoryController.getHabitHistory().addObserver(this);
        habitEvents.clear();
        habitEvents.addAll(HabitHistoryController.getHabitHistory().getEvents());
        adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, habitEvents);
        CL.setAdapter(adapter);
        filterFlag.setText("Filter Off");
        /*
        //"Grabs" data on click and transfer it to second activity to be modified or updated.
         */

        clearFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habitEvents.clear();
                habitEvents.addAll(HabitHistoryController.getHabitHistory().getEvents());
                adapter = new ArrayAdapter<HabitEvent>(HabitHistoryActivity.this, android.R.layout.simple_list_item_1, habitEvents);
                CL.setAdapter(adapter);
                filterFlag.setText("Filter Off");
            }
        });

        CL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(HabitHistoryActivity.this, ViewHabitEvent.class);
                intent.putExtra("View Event", position);
                startActivity(intent);
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitHistoryActivity.this, HabitHistoryFilterActivity.class);
                startActivityForResult(intent,FILTER_REQUEST);
            }
        });
    }

    protected void onStart(){
        super.onStart();
    }

    protected void onDestroy(){
        super.onDestroy();
        HabitHistoryController.saveHabitHistory();
    }

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

    protected void onActivityResult(int request_code, int result_code, Intent filterData){
        super.onActivityResult(request_code,result_code,filterData);
        if(request_code == FILTER_REQUEST){
            if(result_code == FILTER_BY_TYPE){
                filter = filterData.getStringExtra("FILTER TYPE");
                ArrayList<HabitEvent> filteredList = new ArrayList<>();
                for (HabitEvent event : habitEvents){
                    if (event.getEventHabitType().equals(filter)){
                        filteredList.add(event);
                    }
                }
                adapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_list_item_1, filteredList);
                CL.setAdapter(adapter);
                filterFlag.setText("Filter On");
                Toast.makeText(this, "Filter by type passed back: "+ filter,
                        Toast.LENGTH_SHORT).show();
            }
            if(result_code == FILTER_BY_COMMENT){
                filter = filterData.getStringExtra("FILTER TYPE");
                ArrayList<HabitEvent> filteredList = new ArrayList<>();
                for (HabitEvent event : habitEvents){
                    if (event.getComment().contains(filter)){
                        filteredList.add(event);
                    }
                }
                adapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_list_item_1, filteredList);
                CL.setAdapter(adapter);
                filterFlag.setText("Filter On");
                Toast.makeText(this, "Filter by comment passed back: "+ filter,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}