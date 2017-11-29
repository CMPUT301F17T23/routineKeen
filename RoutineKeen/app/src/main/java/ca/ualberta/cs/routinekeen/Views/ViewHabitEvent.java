package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.R;

public class ViewHabitEvent extends AppCompatActivity {
    private String eventType;
    private Integer index;
    private EditText eventTitle;
    private EditText eventComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_event);
        IOManager.initManager(getApplicationContext());
        //Grab data from previous activity
        Intent intent = getIntent();
        index = (Integer) intent.getSerializableExtra("View Event");
        Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        HabitListController.getHabitList();
        ArrayList<String> typeList = new ArrayList<String>(HabitListController.getTypeList());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                typeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                eventType =  (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        //Show respective values in view
        TextView newEventTitle = (TextView) findViewById(R.id.eventTitle);
        newEventTitle.setText(HabitHistoryController.getHabitEvent(index).getTitle());

        TextView newEventComment = (TextView) findViewById(R.id.eventComment);
        newEventComment.setText(HabitHistoryController.getHabitEvent(index).getComment());
    }

    public void saveEvent(View view)
    {
        eventTitle = (EditText) findViewById(R.id.eventTitle);
        eventComment = (EditText) findViewById(R.id.eventComment);
        if(validationSuccess()) {

            HabitHistoryController.getHabitHistory().getHabitEvent(index).setTitle(eventTitle.getText().toString());
            HabitHistoryController.saveHabitHistory();
            HabitHistoryController.getHabitHistory().getHabitEvent(index).setComment(eventComment.getText().toString());
            HabitHistoryController.saveHabitHistory();
            HabitHistoryController.getHabitHistory().getHabitEvent(index).setEventHabitType(eventType);

            finish();
        }
    }
    private boolean validationSuccess() {
        if (eventTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a title for event.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (eventComment.getText().toString().length() > 20) {
            Toast.makeText(this, "Habit event comment much be less than 20 characters.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void delEvent(View view)
    {
        HabitEvent delEvent = HabitHistoryController.getHabitEvent(index);
        HabitHistoryController.removeHabitEvent(delEvent);
        finish();
    }
}
