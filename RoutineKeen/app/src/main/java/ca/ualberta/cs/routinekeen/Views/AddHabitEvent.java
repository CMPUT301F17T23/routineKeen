package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Helpers.DateHelpers;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.R;

public class AddHabitEvent extends AppCompatActivity {
    private String eventType;
    private EditText eventTitle;
    private EditText eventComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);
        IOManager.initManager(this.getApplicationContext());
        Spinner spinner = (Spinner) findViewById(R.id.habitTypeSpinner);
        eventTitle = (EditText) findViewById(R.id.eventTitle);
        eventComment = (EditText) findViewById(R.id.eventComment);
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
    }

    public void addEvent(View view)
    {
        if(validationSuccess()){
        HabitEvent toAddEvent = new HabitEvent(eventTitle.getText().toString(),eventType, eventComment.getText().toString());
        HabitHistoryController.addHabitEvent(toAddEvent);
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

}
