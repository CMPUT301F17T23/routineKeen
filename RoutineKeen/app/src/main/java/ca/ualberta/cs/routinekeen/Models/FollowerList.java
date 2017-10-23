package ca.ualberta.cs.routinekeen.Models;

import java.util.ArrayList;

/**
 * Created by hughc on 2017-10-23.
 */

public class FollowerList {
    private ArrayList<User> followerList;

    public FollowerList(ArrayList<User> followerList) {
        this.followerList = followerList;
    }

    public ArrayList<User> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(ArrayList<User> followerList) {
        this.followerList = followerList;
    }
}
