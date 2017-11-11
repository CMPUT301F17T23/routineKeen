package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.ualberta.cs.routinekeen.R;

/**
 * Created by tiakindele on 2017-11-10.
 */

public class HabitEditActivity extends AppCompatActivity {

    Button cancelBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_habit);

        cancelBtn = (Button) findViewById(R.id.cancelButton);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitEditActivity.this,
                        HabitListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveHabitEdit(View v) {
//        Button saveBtn;
//        saveBtn = (Button) findViewById(R.id.saveButton);

        Toast.makeText(HabitEditActivity.this, "Edit saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HabitEditActivity.this,
                HabitListActivity.class);
        startActivity(intent);
    }
}