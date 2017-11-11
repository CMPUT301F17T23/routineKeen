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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.R;

public class NewHabitActivity extends AppCompatActivity {

    private Button cancelBtn;
    private Button dateDisplay;
    private TextView textViewDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);
        dateDisplay = (Button) findViewById(R.id.addHabit_date);
        textViewDate = (TextView) findViewById(R.id.show_habit_date);

        dateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Calendar c = Calendar.getInstance();
//                int year = c.get(Calendar.YEAR);
//                int month = c.get(Calendar.MONTH);
//                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(
                        NewHabitActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
//                        year,month,day);
                        // setting default value to Nov 01, 2017
                        2017, 10, 01);
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
    }



    @Override
    protected void onStart() {
        super.onStart();

        cancelBtn = (Button) findViewById(R.id.cancel_newHabit);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        NewHabitActivity.this, HabitListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addHabit(View v) {
        HabitListController.getHabitList();
        EditText hTitle = (EditText) findViewById(R.id.addHabit_editText_name);

        if (hTitle.getText().toString() != null && !hTitle.getText().toString().isEmpty()) {
            EditText hReason = (EditText) findViewById(R.id.addHabit_editText_reason);
            TextView hDate = (TextView) findViewById(R.id.show_habit_date);
            Date date = null;
            if (hDate.getText().toString().trim().length() == 0) {

            } else {
                String hDate_string = hDate.getText().toString();
                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Log.d("myTag", "before: " + hDate_string);
                try {
                    date = formatter.parse(hDate_string);
                    Log.d("myTag", "after: " + String.valueOf(date));
                } catch(ParseException e) {
                    e.printStackTrace();
                }
            }
            HabitListController.getHabitList().addHabit(new Habit(hTitle.getText().toString(),
                    hReason.getText().toString(), date));
            Intent intent = new Intent(
                    NewHabitActivity.this, HabitListActivity.class);
            Toast.makeText(this, "Habit added", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Please enter a habit name", Toast.LENGTH_SHORT).show();
        }

    }
}
