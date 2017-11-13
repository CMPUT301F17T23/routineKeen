package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.R;

/**
 * Created by tiakindele on 2017-11-10.
 */

public class HabitDetailsActivity extends AppCompatActivity {

    private Button backBtn;
    private Button editBtn;

    private static final int EDIT_HABIT_REQUEST = 1;

    private String title;
    private String reason;
    private String startDate;

    private int pos;

    private Intent returnIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_habit_details);

        //Habit habit = new Habit();
        returnIntent = new Intent();
        returnIntent.putExtra("habitEdited", false);

        final Bundle data = getIntent().getExtras();

        final TextView titleTextView = (TextView) findViewById(R.id.viewHabit_habitTitleField);
        final TextView reasonTextView = (TextView) findViewById(R.id.viewHabit_habitReasonField);
        final TextView dateTextView = (TextView) findViewById(R.id.viewHabit_habitDateField);

        title = data.getString("title");
        reason = data.getString("reason");
        startDate = data.getString("startDate");
        pos = data.getInt("position");

        titleTextView.setText(title);
        reasonTextView.setText(reason);
        dateTextView.setText(startDate);

        backBtn = (Button) findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        editBtn = (Button) findViewById(R.id.editButton);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitDetailsActivity.this, HabitEditActivity.class);
                intent.putExtras(data);
                startActivityForResult(intent, EDIT_HABIT_REQUEST);
            }
        });

        //set data
//        title.setText(data);

//        String titleStr = habit.getHabitTitle();
//        title.setText(titleStr);
//        String reasonStr = habit.getHabitReason();
//        reason.setText(reasonStr);
//        Date dateDate = habit.getStartDate();
//        date.setText(dateDate.toString());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == EDIT_HABIT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bundle data = intent.getExtras();
                title = data.getString("title");
                reason = data.getString("reason");

                returnIntent = new Intent();
                returnIntent.putExtra("habitEdited", true);
                returnIntent.putExtra("title", title);
                returnIntent.putExtra("reason", reason);
                returnIntent.putExtra("position", pos);
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, returnIntent);
        super.onBackPressed();
    }
}