package ca.ualberta.cs.routinekeen.Models;

import java.util.ArrayList;
import java.util.Collection;

import io.searchbox.annotations.JestId;

/**
 * Created by hughc on 2017-10-23.
 */

public class User {
    private String username;
    private String userID;
    private ArrayList<String> followerList;
    private ArrayList<String> followerRequests;
    private ArrayList<Habit> habits;
    private ArrayList<HabitEvent> habitEvents;

    public User() {}

    public User(String username) {
        this.username = username;
        followerRequests = new ArrayList<String>();
        followerList = new ArrayList<String>();
    }

    public User(String username, String userID) {
        this.username = username;
        this.userID = userID;
        followerRequests = new ArrayList<String>();
        followerList = new ArrayList<String>();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(ArrayList<String> followerList) {
        this.followerList = followerList;
    }

    public ArrayList<String> getFollowerRequests() {
        return followerRequests;
    }

    public void setFollowerRequests(ArrayList<String> followerRequests) {
        this.followerRequests = followerRequests;
    }

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    public void setHabits(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    public ArrayList<HabitEvent> getHabitEvents() {
        return habitEvents;
    }

    public void setHabitEvents(ArrayList<HabitEvent> habitEvents) {
        this.habitEvents = habitEvents;
    }

    @Override
    public String toString() {
        return username;
    }
}
