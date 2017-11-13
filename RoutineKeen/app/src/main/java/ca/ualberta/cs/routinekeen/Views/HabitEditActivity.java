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
 * Created by tiakindele on 2017-11-10.
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

        final Bundle data = getIntent().getExtras();

        titleEditText = (EditText) findViewById(R.id.editHabit_habitTitleField);
        reasonEditText = (EditText) findViewById(R.id.editHabit_habitReasonField);
        titleEditText.setText(data.getString("title"));
        reasonEditText.setText(data.getString("reason"));

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


    public void saveHabitEdit(View v) {
//        Button saveBtn;
//        saveBtn = (Button) findViewById(R.id.saveButton);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", titleEditText.getText().toString());
        returnIntent.putExtra("reason", reasonEditText.getText().toString());

        setResult(RESULT_OK, returnIntent);

        Toast.makeText(HabitEditActivity.this, "Edit saved", Toast.LENGTH_SHORT).show();

        finish();
    }

}