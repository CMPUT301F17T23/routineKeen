package ca.ualberta.cs.routinekeen.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.FindFollowersController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Controllers.UserSingleton;
import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.User;
import ca.ualberta.cs.routinekeen.R;


public class FollowerFeedFrag extends Fragment {
    private static IOManager ioManager = IOManager.getManager();
    private User currentUser = UserSingleton.getCurrentUser();

    private ListView feedListView;
    private ArrayList<String> userFeedList = new ArrayList<String>();
    private ArrayAdapter<String> feedAdapter;

    private ArrayList<String> feedView;


   // private OnFragmentInteractionListener mListener;

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
        /*

        //Calculate feed here given user list
        for(int i = 0; i < userFeedList.size(); i++)
        {
            try{
                User tempUser = ioManager.getUser(userFeedList.get(i));
            }
            catch(NetworkUnavailableException e){

            }



        }
*/

        userFeedList = FindFollowersController.getFeedList(currentUser);
        feedListView = (ListView) view.findViewById(R.id.feedList);
        feedAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, userFeedList);
        feedListView.setAdapter(feedAdapter);

        // Inflate the layout for this fragment
        return view;
    }

}
