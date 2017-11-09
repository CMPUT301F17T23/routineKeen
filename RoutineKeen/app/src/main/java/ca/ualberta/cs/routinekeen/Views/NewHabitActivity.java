package ca.ualberta.cs.routinekeen.Views;

import android.app.DatePickerDialog;
import android.content.Intent;
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

public class NewHabitActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);
    }

    Button cancelBtn;
    Button dateBtn;

    @Override
    protected void onStart() {
        super.onStart();

        dateBtn = (Button) findViewById(R.id.addHabit_date);
        cancelBtn = (Button) findViewById(R.id.cancel_newHabit);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        NewHabitActivity.this, HabitListActivity.class);
                startActivity(intent);
            }
        });
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager(), "date");
            }
        });
    }

    public void addHabit(View v) {
        HabitListController hlc = new HabitListController();
        EditText hTitle = (EditText) findViewById(R.id.addHabit_editText_name);
        EditText hReason = (EditText) findViewById(R.id.addHabit_editText_reason);
        TextView hDate = (TextView) findViewById(R.id.show_habit_date);
        Date date = null;
        if (hDate.getText().toString().trim().length() == 0) {

        } else {
            String hDate_string = hDate.getText().toString();
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            try {
                date = format.parse(hDate_string);
            } catch(ParseException e) {
                e.printStackTrace();
            }
        }

        hlc.addHabit(new Habit(hTitle.getText().toString(),
                hReason.getText().toString(), date));
        Intent intent = new Intent(
                NewHabitActivity.this, HabitListActivity.class);
        Toast.makeText(this, "Habit added", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.show_habit_date)).setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }
    }
}
