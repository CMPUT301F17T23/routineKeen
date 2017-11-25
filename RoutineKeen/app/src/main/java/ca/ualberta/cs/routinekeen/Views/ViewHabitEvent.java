package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.R;

public class ViewHabitEvent extends AppCompatActivity {
    private Integer index;
    private HabitEvent habitEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_event);
        IOManager.initManager(getApplicationContext());
        //Grab data from previous activity
        Intent intent = getIntent();
        index = (Integer) intent.getSerializableExtra("View Event");

        //Show respective values in view
        TextView newEventTitle = (TextView) findViewById(R.id.eventTitle);
        newEventTitle.setText(HabitHistoryController.getHabitEvent(index).getTitle());

        TextView newEventComment = (TextView) findViewById(R.id.eventComment);
        newEventComment.setText(HabitHistoryController.getHabitEvent(index).getComment());
    }

    public void saveEvent(View view)
    {
        EditText eventTitle = (EditText) findViewById(R.id.eventTitle);
        EditText eventComment = (EditText) findViewById(R.id.eventComment);

        String newEventTitle = eventTitle.getText().toString();
        String newEventComment = eventComment.getText().toString();

        if(newEventTitle.isEmpty() || newEventTitle=="\n")
        {
            newEventTitle = "No Title";
        }
        if(newEventComment.isEmpty() || newEventComment=="\n")
        {
            newEventComment = "No Comments debug debug";
        }

        HabitHistoryController.getHabitHistory().getHabitEvent(index).setTitle(newEventTitle);
        HabitHistoryController.saveHabitHistory();
        HabitHistoryController.getHabitHistory().getHabitEvent(index).setComment(newEventComment);
        HabitHistoryController.saveHabitHistory();

        Intent intent = new Intent();
        intent.putExtra("Viewed Event", newEventTitle + "\n" + newEventComment);
        setResult(RESULT_OK, intent);

        finish();
    }

    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra("Viewed Event", "");
        setResult(RESULT_OK, intent);

        finish();
    }

    public void delEvent(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("Viewed Event", "Delete");
        setResult(RESULT_OK, intent);

        finish();
    }
}