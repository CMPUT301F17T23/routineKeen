package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.R;

public class AddHabitEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);
        IOManager.initManager(this.getApplicationContext());
    }

    public void addEvent(View view)
    {
        //Edit texts, there will be more
        EditText eventTitle = (EditText) findViewById(R.id.eventTitle);
        EditText eventComment = (EditText) findViewById(R.id.eventComment);

        assert eventTitle != null;
        String newEventTitle = eventTitle.getText().toString();
        assert eventComment != null;
        String newEventComment = eventComment.getText().toString();

        if(newEventTitle.isEmpty() || newEventTitle.equals("\n"))
        {
            newEventTitle = "No Title";
        }
        if(newEventComment.isEmpty() || newEventComment.equals("\n"))
        {
            newEventComment = "No Comments";
        }
        HabitEvent toAddEvent = new HabitEvent(newEventTitle,newEventComment);
        HabitHistoryController.addHabitEvent(toAddEvent);
        finish();
    }
}
