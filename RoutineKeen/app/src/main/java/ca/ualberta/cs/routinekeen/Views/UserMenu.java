package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Controllers.UserSingleton;
import ca.ualberta.cs.routinekeen.R;

/**
 * Created by tiakindele on 2017-11-06.
 */

public class UserMenu extends AppCompatActivity{

    private Button logoutBtn;
    private TextView viewHabitList;
    private TextView viewHabitSchedule;
    private TextView viewHabitHistory;
    private TextView userSocialMedia;
    private TextView geoAndMaps;
    private TextView loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_menu);
        logoutBtn = (Button) findViewById(R.id.logout);
        viewHabitList = (TextView) findViewById(R.id.view_habit_list);
        viewHabitSchedule = (TextView) findViewById(R.id.view_habit_schedule);
        viewHabitHistory = (TextView) findViewById(R.id.view_habit_history);
        userSocialMedia = (TextView) findViewById(R.id.user_social_media);
        geoAndMaps = (TextView) findViewById(R.id.geo_and_maps);
        loggedInUser = (TextView) findViewById(R.id.username);

        String currentUser = "Logged in as: " + UserSingleton.getCurrentUser().getUsername();
        loggedInUser.setText(currentUser);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final IOManager ioManager = IOManager.getManager();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ioManager.clearUserSharedPrefs();
                finish();
            }
        });

        viewHabitList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this, HabitListActivity.class);
                startActivity(intent);
            }
        });

        viewHabitSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this, HabitScheduleActivity.class);
                startActivity(intent);
            }
        });

        //Edited to Habit History for test- mikeev
        viewHabitHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this, HabitHistoryActivity.class);
                startActivity(intent);
            }
        });

        userSocialMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserMenu.this, HabitFollowingSharing.class);
                startActivity(intent);
            }
        });

        geoAndMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this, MapFilter.class);
                startActivity(intent);
            }
        });
    }
}
