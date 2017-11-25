package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
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
    private HabitEvent viewEvent;
    private int viewPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                HabitEvent habitevent = HabitHistoryController.getHabitEvent(position);
                //save object and position
                viewEvent = habitevent;
                viewPosition = position;
                intent.putExtra("View Event", position);
                startActivityForResult(intent, 1);
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



        public void changeHabitEvent(String eData)
        {
            if(!eData.isEmpty() && !eData.equals("Delete"))
            {
                String values[] = eData.split("\\r?\\n");
                viewEvent.setTitle(values[0]);
                viewEvent.setComment(values[1]);

                habitEvents.set(viewPosition, viewEvent);
                CL = (ListView) findViewById(R.id.habitHistoryList);

                adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, habitEvents);
                CL.setAdapter(adapter);


            }
            else if(eData.equals("Delete"))
            {
                HabitHistoryController.removeHabitEvent(viewEvent);
                CL = (ListView) findViewById(R.id.habitHistoryList);

                adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, habitEvents);
                CL.setAdapter(adapter);

            }

        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            //TODO Auto-generated method stub
            super.onActivityResult(requestCode, resultCode, data);
            //viewHabit event
            if(requestCode == 1)
            {
                if(resultCode == RESULT_OK)
                {
                    String eData = data.getStringExtra("Viewed Event");
                    changeHabitEvent(eData);
                }
            }
        }
    /*
        public void filterList(View view)
        {

        }
    */
    @Override
    public void update(Observable observable, Object o) {
        habitEvents.clear();
        habitEvents.addAll(HabitHistoryController.getHabitHistory().getEvents());
        adapter.notifyDataSetChanged();
    }
}