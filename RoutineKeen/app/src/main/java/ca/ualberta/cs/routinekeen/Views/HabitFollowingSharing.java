package ca.ualberta.cs.routinekeen.Views;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.FindFollowersController;
import ca.ualberta.cs.routinekeen.Helpers.ViewPagerAdapter;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.R;
import ca.ualberta.cs.routinekeen.Controllers.UserSingleton;

/**
 * Activity class used as a base for custom social media
 *
 * @author Mikee V
 * @see FollowerFeedFrag
 * @see FollowRequestsFrag
 * @see FindFollowersFrag
 * @see ViewPagerAdapter
 * @version 1.0.0
 */
public class HabitFollowingSharing extends AppCompatActivity {

    private User currentUser;
    private int currentUserPosition;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_following_sharing2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentUser = UserSingleton.getCurrentUser();
        //Set adapters
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new FollowerFeedFrag(), "Follow Feed");
        viewPagerAdapter.addFragments(new FindFollowersFrag(), "Find Users");
        viewPagerAdapter.addFragments(new FollowRequestsFrag(), "Follow Requests");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
