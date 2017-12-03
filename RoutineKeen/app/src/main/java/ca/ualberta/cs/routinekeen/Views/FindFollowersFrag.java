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
import ca.ualberta.cs.routinekeen.Controllers.UserSingleton;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.R;

public class FindFollowersFrag extends Fragment {

    private User currentUser = UserSingleton.getCurrentUser();


    public FindFollowersFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_followers, container, false);

        Button btn = (Button) view.findViewById(R.id.searchButton);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Implement the code to run on button click here
                EditText userSearch = (EditText) getActivity().findViewById(R.id.userSearch);
                String userToSearch = userSearch.getText().toString();

                User requestedUser = FindFollowersController.getUsers(userToSearch);
                if(requestedUser == null)
                {
                    //Print out user does not exist
                    Toast.makeText(getActivity(), "User does not exist. Please try again.", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //User exist, add to their request list
                    if(requestedUser.getFollowerRequests().contains(currentUser))
                    {
                        Toast.makeText(getActivity(), "You have already made a request previously.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        FindFollowersController.addToRequestList(currentUser, requestedUser);
                    }

                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
