package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.R;

/*
 - Change android manifest so that main menu is parent and habit history is child, so that when back
  button pressed when on main menu, it will go to main menu (onBackPressed(){//do something})

 -Add filter later
 */
public class HabitHistoryActivity extends AppCompatActivity {

    private ListView CL;
    private ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();
    private ArrayAdapter<HabitEvent> adapter;
    private HabitHistory habitHistory = new HabitHistory(habitEvents);// For controller purposes later

    private HabitEvent viewEvent;
    private int viewPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history);


        CL = (ListView) findViewById(R.id.habitHistoryList);
        adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, habitEvents);
        CL.setAdapter(adapter);

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
        adapter = new ArrayAdapter<HabitEvent>(this, android.R.layout.simple_list_item_1, counters);
        CL.setAdapter(adapter);
    }
    */

    public void addHabitEvent(View view)
    {
        Intent intent = new Intent(this, AddHabitEvent.class);
        //// TODO: 11/12/2017 habitEvent needs to have title and habit type as arguments for constructor 
//        habitEvents.add(new HabitEvent());
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
        else//is empty
        {
            //Nothing happens


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
}