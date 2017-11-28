package ca.ualberta.cs.routinekeen.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.FollowerList;
import ca.ualberta.cs.routinekeen.Controllers.FollowerListController;
import ca.ualberta.cs.routinekeen.R;


public class HabitFollowingSharing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_following_sharing);
    }

    public void findFollowers(View view)
    {
        Intent intent = new Intent(this, FindFollowersActivity.class);
        startActivity(intent);
    }

    public void followerRequest(View view)
    {
        Intent intent = new Intent(this, FollowerRequestActivity.class);
        startActivity(intent);
    }

    public void follwerFeed(View view)
    {
        Intent intent = new Intent(this, FollowerFeedActivity.class);
        startActivity(intent);
    }

}
