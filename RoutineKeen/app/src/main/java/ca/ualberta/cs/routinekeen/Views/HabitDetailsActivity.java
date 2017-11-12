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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_habit_details);

        Habit habit = new Habit();

        final Bundle data = getIntent().getExtras();

        final TextView title = (TextView) findViewById(R.id.viewHabit_habitTitleField);
        final TextView reason = (TextView) findViewById(R.id.viewHabit_habitReasonField);
        final TextView date = (TextView) findViewById(R.id.viewHabit_habitDateField);

        backBtn = (Button) findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitDetailsActivity.this,
                        HabitListActivity.class);
                startActivity(intent);
            }
        });

        editBtn = (Button) findViewById(R.id.editButton);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitDetailsActivity.this, HabitEditActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        })

        //set data
//        title.setText(data);

//        String titleStr = habit.getHabitTitle();
//        title.setText(titleStr);
//        String reasonStr = habit.getHabitReason();
//        reason.setText(reasonStr);
//        Date dateDate = habit.getStartDate();
//        date.setText(dateDate.toString());

    }
}