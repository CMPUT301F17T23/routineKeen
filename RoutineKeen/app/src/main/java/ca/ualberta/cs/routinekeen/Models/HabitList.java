package ca.ualberta.cs.routinekeen.Models;

import java.util.ArrayList;

/**
 * Created by hughc on 2017-10-23.
 */

public class HabitList {
    private ArrayList<Habit> habitList;

    public HabitList(){
        habitList = new ArrayList<Habit>();
    }

    public HabitList(ArrayList<Habit> habitList) {
        this.habitList = habitList;
    }

    public ArrayList<Habit> getHabitList() {
        return habitList;
    }

    public void setHabitList(ArrayList<Habit> habitList) {
        this.habitList = habitList;
    }

    public void addHabit(Habit habit){
        habitList.add(habit);
    }

    public Habit getHabitByType(String type) {
        return null; // implement
    }

    public boolean removeHabitByType(String type){
        return true;
    }

    public int habitListSize(){
        return habitList.size();
    }
}
