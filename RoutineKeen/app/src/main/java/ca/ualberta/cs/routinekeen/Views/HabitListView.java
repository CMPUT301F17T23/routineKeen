package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import ca.ualberta.cs.routinekeen.R;

/**
 * Created by tiakindele on 2017-11-07.
 */

public class HabitListView extends AppCompatActivity {

    ImageButton addHabitBtn;

    @Override
    protected void onStart() {
        super.onStart();
        addHabitBtn = (ImageButton) findViewById(R.id.addNewHabit);

        addHabitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitListView.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_list);

        onStart();
    }
}
