package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.R;

/**
 * Allows the user to view, edit, and save the details of the selected habit, and create a
 * schedule for what days the habit will be performed
 *
 * @author  RoutineKeen
 * @see     HabitListActivity
 * @see     ca.ualberta.cs.routinekeen.Models.Habit
 * @version 1.0.0
 */

public class HabitEditActivity extends AppCompatActivity {
    private Button cancelBtn;
    private Button saveBtn;
    private EditText titleEditText;
    private EditText reasonEditText;
    private EditText dateEditText;
    private Switch monSwitch;
    private Switch tueSwitch;
    private Switch wedSwitch;
    private Switch thuSwitch;
    private Switch friSwitch;
    private Switch satSwitch;
    private Switch sunSwitch;
    private Bundle data = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_habit);
        titleEditText = (EditText) findViewById(R.id.editHabit_habitTitleField);
        reasonEditText = (EditText) findViewById(R.id.editHabit_habitReasonField);
        dateEditText = (EditText) findViewById(R.id.editHabit_habitStartDateField);
        saveBtn = (Button) findViewById(R.id.saveButton);
        monSwitch = (Switch) findViewById(R.id.monSwitch);
        tueSwitch = (Switch) findViewById(R.id.tueSwitch);;
        wedSwitch = (Switch) findViewById(R.id.wedSwitch);;
        thuSwitch = (Switch) findViewById(R.id.thuSwitch);
        friSwitch = (Switch) findViewById(R.id.friSwitch);;
        satSwitch = (Switch) findViewById(R.id.satSwitch);;
        sunSwitch = (Switch) findViewById(R.id.sunSwitch);;
        initListeners();
    }

    @Override
    public void onStart(){
        super.onStart();
        data = getIntent().getExtras();
        titleEditText.setText(data.getString("title"));
        reasonEditText.setText(data.getString("reason"));
        dateEditText.setText(data.getString("startDate"));
        setDaySwitches();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        HabitListController.saveHabitList();
    }

    private void initListeners(){
        cancelBtn = (Button) findViewById(R.id.cancelButton);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(HabitEditActivity.this,
//                        HabitListActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        saveBtn = (Button) findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String reason = reasonEditText.getText().toString();
                HabitListController.updateHabit(title, reason, getDaysChecked(),
                        data.getInt("position"));
//                Intent intent = new Intent(HabitEditActivity.this,
//                        HabitListActivity.class);
//                startActivity(intent);
                finish();

            }
        });
    }

    private void setDaySwitches(){
        ArrayList<String> habitSchedDays = data.getStringArrayList("scheduledDays");
        if(habitSchedDays.contains("Mon"))
            monSwitch.setChecked(true);
        if(habitSchedDays.contains("Tue"))
            tueSwitch.setChecked(true);
        if(habitSchedDays.contains("Wed"))
            wedSwitch.setChecked(true);
        if(habitSchedDays.contains("Thu"))
            thuSwitch.setChecked(true);
        if(habitSchedDays.contains("Fri"))
            friSwitch.setChecked(true);
        if(habitSchedDays.contains("Sat"))
            satSwitch.setChecked(true);
        if(habitSchedDays.contains("Sun"))
            sunSwitch.setChecked(true);
    }

    private ArrayList<String> getDaysChecked(){
        ArrayList<String> daysSet = new ArrayList<String>();
        if(monSwitch.isChecked()){
            daysSet.add("Mon");
        }
        if(tueSwitch.isChecked()){
            daysSet.add("Tue");
        }
        if(wedSwitch.isChecked()){
            daysSet.add("Wed");
        }
        if(thuSwitch.isChecked()){
            daysSet.add("Thu");
        }
        if(friSwitch.isChecked()){
            daysSet.add("Fri");
        }
        if(satSwitch.isChecked()){
            daysSet.add("Sat");
        }
        if(sunSwitch.isChecked()){
            daysSet.add("Sun");
        }
        return daysSet;
    }
}