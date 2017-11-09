package ca.ualberta.cs.routinekeen.Models;

import java.util.ArrayList;
import java.util.Collection;

import io.searchbox.annotations.JestId;

/**
 * Created by hughc on 2017-10-23.
 */

public class User {
    private String username;
    @JestId
    private String userID;
    private ArrayList<User> followerList;
    private ArrayList<User> followerRequests;
    private ArrayList<Habit> habits;
    private ArrayList<HabitEvent> habitEvents;

    public User() {

    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String userID) {
        this.username = username;
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String UUID) {
        this.userID = userID;
    }

    public String getUsername() {
        return "Toluwanimi";
        //return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<User> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(ArrayList<User> followerList) {
        this.followerList = followerList;
    }

    public ArrayList<User> getFollowerRequests() {
        return followerRequests;
    }

    public void setFollowerRequests(ArrayList<User> followerRequests) {
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
}
