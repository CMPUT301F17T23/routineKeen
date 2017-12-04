package ca.ualberta.cs.routinekeen.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.ualberta.cs.routinekeen.Controllers.FindFollowersController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Controllers.UserSingleton;
import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.R;

public class FindFollowersFrag extends Fragment {
    private static IOManager ioManager = IOManager.getManager();
    private User currentUser;


    public FindFollowersFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_followers, container, false);

        try{
            currentUser = ioManager.getUser(UserSingleton.getCurrentUser().getUsername());
        }catch (NetworkUnavailableException e){

        }

        Button btn = (Button) view.findViewById(R.id.searchButton);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Implement the code to run on button click here
                EditText userSearch = (EditText) getActivity().findViewById(R.id.userSearch);
                String userToSearch = userSearch.getText().toString();
                User requestedUser = null;
                try{
                    requestedUser = ioManager.getUser(userToSearch);
                }catch(NetworkUnavailableException e){
                    Toast.makeText(getActivity(), "Network Unavailable. Try again later.", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }

                if(requestedUser == null)
                {
                    //Print out user does not exist
                    Toast.makeText(getActivity(), "User does not exist. Please try again.", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //User exist, add to their request list
                    try{
                        requestedUser = ioManager.getUser(userToSearch);
                    }catch(NetworkUnavailableException e){
                        Toast.makeText(getActivity(), "Network Unavailable. Try again later.", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }

                    if(FindFollowersController.getFollowerRequests(requestedUser).contains(currentUser.getUsername()))
                    {
                        Toast.makeText(getActivity(), "You have already made a request previously.", Toast.LENGTH_SHORT).show();
                    }
                    else if(FindFollowersController.getFeedList(currentUser).contains(requestedUser.getUsername()))
                    {
                        Toast.makeText(getActivity(), "You already follow this User", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        FindFollowersController.addToRequestList(currentUser, requestedUser);
                        Toast.makeText(getActivity(), "Your request has been sent", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
