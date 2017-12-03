package ca.ualberta.cs.routinekeen.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.UserSingleton;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.R;



public class FollowRequestsFrag extends Fragment {
    private User currentUser = UserSingleton.getCurrentUser();

    private String clickedUser;
    private int clickedPosition;
    //Find followers
    private ListView findListView;
    private ArrayList<String> userRequestList = new ArrayList<String>();
    private ArrayAdapter<String> findAdapter;
    private User requestedUser;

    public FollowRequestsFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_requests, container, false);
        //Find followers
        //userRequestList = currentUser.getFollowerRequests();//get follower request as arraylist should maybe get from data
        userRequestList.add(("test1"));
        userRequestList.add(("test2"));
        findListView = (ListView) view.findViewById(R.id.followerRequestList);
        findAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, userRequestList);
        if(findAdapter != null){
            findListView.setAdapter(findAdapter);
        }

        //Click on lsitview to get user
        findListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                clickedUser = userRequestList.get(position);
                Toast.makeText(getActivity(), "Please choose Allow or deny for: " + clickedUser, Toast.LENGTH_SHORT).show();
            }

        });

        Button denyBtn = (Button) view.findViewById(R.id.Deny);
        Button allowBtn = (Button) view.findViewById(R.id.Allow);

        denyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /*Must choose a user from list view first clickeduser == null
                * Remove from request list
                * add to following list feed or not
                */
            }
        });

        allowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });



        // Inflate the layout for this fragment
        return view;
    }

}
