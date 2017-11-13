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

import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.R;

/*

 -Add filter later
 */
public class HabitHistoryActivity extends AppCompatActivity implements Observer{

    private ListView CL;
    Collection<HabitEvent> events = HabitHistoryController.getHabitHistory().getEvents();
    private ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>(events);
    private ArrayAdapter<HabitEvent> adapter;
    //private HabitHistory habitHistory = new HabitHistory(habitEvents);// For controller purposes later
    //private HabitHistoryController habitHistoryController = new HabitHistoryController();
//    private HabitHistoryController hhc = new HabitHistoryController();

    private HabitEvent viewEvent;
    private int viewPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history);
        IOManager.initManager(getApplicationContext());
        HabitHistoryController.getHabitHistory().addObserver(this);
        CL = (ListView) findViewById(R.id.habitHistoryList);
        adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, habitEvents);
        CL.setAdapter(adapter);


//        hhc.addObvToHistory();

        /*
        //"Grabs" data on click and transfer it to second activity to be modified or updated.
         */

        CL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(HabitHistoryActivity.this, ViewHabitEvent.class);
                HabitEvent habitevent = habitEvents.get(position);//Controller ???
                //save object and position
                viewEvent = habitevent;
                viewPosition = position;

                intent.putExtra("View Event", habitevent);
                startActivityForResult(intent, 1);
            }
        });
    }

    /*
    //Load Up User Habit history here
    @Override
    protected void onStart() {
        super.onStart();
        //Load Function

        adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, habitEvents);
        CL.setAdapter(adapter);
    }
    */

    public void addHabitEvent(View view)
    {
        Intent intent = new Intent(this, AddHabitEvent.class);
        //// TODO: 11/12/2017 habitEvent needs to have title and habit type as arguments for constructor 
        habitEvents.add(new HabitEvent("title", "random type"));//test
        adapter.notifyDataSetChanged();
        CL = (ListView) findViewById(R.id.habitHistoryList);

        HabitEvent habitevent = habitEvents.get(habitEvents.size() - 1);
        viewPosition = (habitEvents.size() - 1);
        viewEvent = habitevent;
        intent.putExtra("Add Event", habitevent);
        startActivityForResult(intent, 2);
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

            //SAVE FUNCTION HERE

        }
        else if(eData.equals("Delete"))
        {
            habitEvents.remove(viewPosition);
            CL = (ListView) findViewById(R.id.habitHistoryList);

            adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, habitEvents);
            CL.setAdapter(adapter);

            //SAVE FUNCTION HERE
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
        //Add new event
        if(requestCode == 2)
        {
            if(resultCode == RESULT_OK)
            {
                String eData = data.getStringExtra("Added Event");
                changeHabitEvent(eData);
            }
        }
    }

    public void filterList(View view)
    {

    }

    @Override
    public void update(Observable observable, Object o) {

    }
}