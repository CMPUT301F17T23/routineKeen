package ca.ualberta.cs.routinekeen.Views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.UserSingleton;
import ca.ualberta.cs.routinekeen.Helpers.DateHelpers;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.R;

/**
 * View that allows user to input habit parameters, and creates a new habit from the user's input
 *
 * @author  RoutineKeen
 * @see     HabitEditActivity
 * @version 1.0.0
 */

public class NewHabitActivity extends AppCompatActivity {

    private Button addHabitButton;
    private Button cancelBtn;
    private Button dateDisplay;
    private TextView textViewDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private EditText hTitle;
    private EditText hReason;
    private TextView hDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);
        dateDisplay = (Button) findViewById(R.id.addHabit_date);
        textViewDate = (TextView) findViewById(R.id.show_habit_date);
        cancelBtn = (Button) findViewById(R.id.cancel_newHabit);
        addHabitButton = (Button) findViewById(R.id.add_newHabit);
        hTitle = (EditText) findViewById(R.id.addHabit_editText_name);
        hReason = (EditText) findViewById(R.id.addHabit_editText_reason);
        hDate = (TextView) findViewById(R.id.show_habit_date);
        initListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initListeners(){
        dateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Taken from: https://stackoverflow.com/questions/6451837/
                // how-do-i-set-the-current-date-in-a-datepicker
                // Date: Nov 12, 2017
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(
                        NewHabitActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                textViewDate.setText(date);
            }
        };

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(
//                        NewHabitActivity.this, HabitListActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validationSuccess()){
                    Date date = DateHelpers.formatStringToDate(
                            hDate.getText().toString(), "MM/dd/yyyy");
                    String title = hTitle.getText().toString();
                    String reason = hReason.getText().toString();
                    Habit habitToAdd = new Habit(title, reason, date);
                    habitToAdd.setHabitUserID(UserSingleton.getCurrentUser().getUserID());
                    String [] days = new String [] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
                    ArrayList<String> practiceDays = new ArrayList<>(Arrays.asList(days));
                    habitToAdd.setScheduledHabitDays(practiceDays);
                    HabitListController.addHabit(habitToAdd);
//                    Intent intent = new Intent(NewHabitActivity.this, HabitListActivity.class);
//                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean validationSuccess() {
        if (hTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a habit name.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (hReason.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a habit reason.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (hDate.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please enter a start date.",
                    Toast.LENGTH_SHORT).show();
            return false;

        } else {
            Date hDateInput = DateHelpers.formatStringToDate(
                    hDate.getText().toString(), "MM/dd/yyyy");
            Date currentDate = DateHelpers.removeTime(new Date());
            if (hDateInput.before(currentDate)) {
                Toast.makeText(this, "Date is in the past, try again.",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
}