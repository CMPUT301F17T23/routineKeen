package ca.ualberta.cs.routinekeen.Views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.FindFollowersController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Controllers.UserSingleton;
import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.R;


public class FollowerFeedFrag extends Fragment {
    private static IOManager ioManager = IOManager.getManager();
    private User currentUser = UserSingleton.getCurrentUser();

    private ListView feedListView;
    private ArrayList<String> userFeedList = new ArrayList<String>();
    private ArrayAdapter<String> feedAdapter;

    private ArrayList<String> feedView = new ArrayList<String>();
    private HabitHistory usersHabitEvents;
    private HabitList habitList;

    private String clickedUser;
    private int clickedPosition;

    public FollowerFeedFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower_feed, container, false);

        try{
            currentUser = ioManager.getUser(UserSingleton.getCurrentUser().getUsername());
        }catch(NetworkUnavailableException e){

        }

        userFeedList = FindFollowersController.getFeedList(currentUser);
        //Calculate feed here given user list
        for(int i = 0; i < userFeedList.size(); i++)
        {
            User tempUser = null;
            try{
                tempUser = ioManager.getUser(userFeedList.get(i));
            }
            catch(NetworkUnavailableException e){
            }

            try {
                usersHabitEvents = FindFollowersController.getHabitHistory(tempUser);
                HabitEvent recentEvent = usersHabitEvents.getHabitEvent(usersHabitEvents.getSize() - 1);//Gets most reecnt event
                String eventTitle = recentEvent.getTitle();
                String habitType = recentEvent.getEventHabitType();

                feedView.add(tempUser.getUsername() + "\nMost Recent Habit: " + habitType + "\nEvent Title:" + eventTitle);
            }catch (Exception e){

            }

        }



        feedListView = (ListView) view.findViewById(R.id.feedList);
        feedAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, feedView);//Change to user list view for users only
        feedListView.setAdapter(feedAdapter);

        feedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                clickedUser = userFeedList.get(position);
                clickedPosition = position;
                Intent intent = new Intent(getActivity(), FollowerHistoryActivity.class);
                intent.putExtra("Follower Name", clickedUser);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

}
