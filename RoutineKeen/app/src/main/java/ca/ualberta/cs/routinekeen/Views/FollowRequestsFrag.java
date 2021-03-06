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

import ca.ualberta.cs.routinekeen.Controllers.FindFollowersController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Controllers.UserSingleton;
import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.R;



public class FollowRequestsFrag extends Fragment {
    private static IOManager ioManager = IOManager.getManager();
    private User currentUser;

    private String clickedUser;
    private int clickedPosition = -1;
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

        try{
            currentUser = ioManager.getUser(UserSingleton.getCurrentUser().getUsername());
        }catch(NetworkUnavailableException e){

        }
        //Find followers
        userRequestList = FindFollowersController.getFollowerRequests(currentUser);
        findListView = (ListView) view.findViewById(R.id.followerRequestList);
        findAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, userRequestList);
        if(findAdapter != null){
            findListView.setAdapter(findAdapter);
        }

        //Click on listview to get user
        findListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                clickedUser = userRequestList.get(position);
                clickedPosition = position;
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

                if(clickedPosition != -1) {
                    userRequestList.remove(clickedPosition);
                    findAdapter.notifyDataSetChanged();
                    try {
                        currentUser = ioManager.getUser(UserSingleton.getCurrentUser().getUsername());
                        requestedUser = ioManager.getUser(clickedUser);
                        ioManager.respondToFollowerRequest(requestedUser, currentUser, false);

                    } catch (NetworkUnavailableException e) {

                    }
                } else{
                    Toast.makeText(getActivity(), "Please click on a user before" +
                            " accept/reject of request.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        allowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(clickedPosition != -1) {
                    userRequestList.remove(clickedPosition);
                    findAdapter.notifyDataSetChanged();
                    try {
                        currentUser = ioManager.getUser(UserSingleton.getCurrentUser().getUsername());
                        requestedUser = ioManager.getUser(clickedUser);
                        ioManager.respondToFollowerRequest(requestedUser, currentUser, true);
                    } catch (NetworkUnavailableException e) {

                    }
                } else{
                    Toast.makeText(getActivity(), "Please click on a user before" +
                            " accept/reject of request.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        // Inflate the layout for this fragment
        return view;
    }

}
