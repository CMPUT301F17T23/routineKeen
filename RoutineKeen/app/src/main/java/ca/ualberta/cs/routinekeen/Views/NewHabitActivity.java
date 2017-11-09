package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.R;

public class NewHabitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);
    }

    Button cancelBtn;

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
        HabitListController hlc = new HabitListController();
        EditText hTitle = (EditText) findViewById(R.id.addHabit_editText_name);
        EditText hReason = (EditText) findViewById(R.id.addHabit_editText_reason);
        EditText hDate = (EditText) findViewById(R.id.addHabit_editText_date);
        hlc.addHabit(new Habit(hTitle.getText().toString(),
                hReason.getText().toString(), null));
        Intent intent = new Intent(
                NewHabitActivity.this, HabitListActivity.class);
        Toast.makeText(this, "Habit added", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
