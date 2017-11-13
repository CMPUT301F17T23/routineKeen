package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.ualberta.cs.routinekeen.R;

/**
 * Allows the user to edit and update the details of the selected activity
 *
 * @author  RoutineKeen
 * @see     HabitDetailsActivity
 * @version 1.0.0
 */

public class HabitEditActivity extends AppCompatActivity {
    Button cancelBtn;
    Button saveBtn;
    EditText titleEditText;
    EditText reasonEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_habit);
        titleEditText = (EditText) findViewById(R.id.editHabit_habitTitleField);
        reasonEditText = (EditText) findViewById(R.id.editHabit_habitReasonField);
        saveBtn = (Button) findViewById(R.id.saveButton);
        initListeners();
    }

    @Override
    public void onStart(){
        super.onStart();
        final Bundle data = getIntent().getExtras();
        titleEditText.setText(data.getString("title"));
        reasonEditText.setText(data.getString("reason"));
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public void saveHabitEdit(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", titleEditText.getText().toString());
        returnIntent.putExtra("reason", reasonEditText.getText().toString());

        setResult(RESULT_OK, returnIntent);

        Toast.makeText(HabitEditActivity.this, "Edit saved", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void initListeners(){
        cancelBtn = (Button) findViewById(R.id.cancelButton);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitEditActivity.this,
                        HabitListActivity.class);
                startActivity(intent);
            }
        });

        saveBtn = (Button) findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHabitEdit(view);
            }
        });
    }

}