package ca.ualberta.cs.routinekeen.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ca.ualberta.cs.routinekeen.R;

public class FindFollowersFrag extends Fragment {

    //private OnFragmentInteractionListener mListener;

    public FindFollowersFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_followers, container, false);
    }
/*
    public void searchUser(View view)
    {
        EditText userSearch = (EditText) findViewById(R.id.userSearch);
    }*/
}
