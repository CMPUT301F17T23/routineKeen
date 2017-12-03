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

public class HabitFollowingSharing extends AppCompatActivity {

    private User currentUser;
    private int currentUserPosition;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    //Find followers
    private ListView findListView;
    private ArrayList<String> userSearchList = new ArrayList<String>();
    private ArrayAdapter<String> findAdapter;
    private User requestedUser;

    //Follower Feed
    private ListView feedListView;
    private ArrayList<String> userFeedList = new ArrayList<String>();
    private ArrayAdapter<User> feedAdapter;

    //Follow Request
    private ArrayList<String> userRequestList = new ArrayList<String>();

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
        viewPagerAdapter.addFragments(new FindFollowersFrag(), "Find Users");
        viewPagerAdapter.addFragments(new FollowerFeedFrag(), "Follow Feed");
        viewPagerAdapter.addFragments(new FollowRequestsFrag(), "Follow Requests");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        //userRequestList = currentUser.getFollowerRequests();
    }

    /*Takes string from usersearch edit text
     *user that string gets all users that has substring(ordered?) or char sequence with usersearch
     * stored in an array list put to listview
     * onclick list view send request
     */
    /*public void searchUser(View view)
    {
        EditText userSearch = (EditText) findViewById(R.id.userSearch);
        String userToSearch = userSearch.getText().toString();

        requestedUser = FindFollowersController.getUsers(userToSearch);
        if(currentUser == null)
        {
            //Print out user does not exist
            Toast.makeText(this, "User does not exist. Please try again.", Toast.LENGTH_SHORT).show();

        }
        else
        {
            //User exist, add to their request list
            if(requestedUser.getFollowerRequests().contains(currentUser))
            {
                Toast.makeText(this, "You have already made a request previously.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                FindFollowersController.addToRequestList(currentUser, requestedUser);
            }

        }
    }*/




}
