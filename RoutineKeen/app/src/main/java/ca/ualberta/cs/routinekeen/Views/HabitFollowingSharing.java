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

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.FindFollowersController;
import ca.ualberta.cs.routinekeen.Helpers.ViewPagerAdapter;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.R;

public class HabitFollowingSharing extends AppCompatActivity {

    private User currentUser;
    private int currentUserPosition;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    //Find followers
    private ListView findListView;
    private ArrayList<User> userSearchList = new ArrayList<User>();
    private ArrayAdapter<User> findAdapter;

    //Follower Feed
    private ListView feedListView;
    private ArrayList<User> userFeedList = new ArrayList<User>();
    private ArrayAdapter<User> feedAdapter;

    //Follow Request


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_following_sharing2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set adapters
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new FindFollowersFrag(), "Find Users");
        viewPagerAdapter.addFragments(new FollowerFeedFrag(), "Follow Feed");
        viewPagerAdapter.addFragments(new FollowRequestsFrag(), "Follow Requests");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /*Takes string from usersearch edit text
     *user that string gets all users that has substring(ordered?) or char sequence with usersearch
     * stored in an array list put to listview
     * onclick list view send request
     */
    public void searchUser(View view)
    {
        EditText userSearch = (EditText) findViewById(R.id.userSearch);
        String userToSearch = userSearch.getText().toString();

        userSearchList = FindFollowersController.getUsers(userToSearch);
    }




}
