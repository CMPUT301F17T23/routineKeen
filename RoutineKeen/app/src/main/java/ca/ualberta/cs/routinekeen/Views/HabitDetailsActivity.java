package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ca.ualberta.cs.routinekeen.R;

/**
 * Created by tiakindele on 2017-11-10.
 */

public class HabitDetailsActivity extends AppCompatActivity {

    Button backBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_habit_details);

        backBtn = (Button) findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitDetailsActivity.this,
                        HabitListActivity.class);
                startActivity(intent);
            }
        });
    }
}