package ca.ualberta.cs.routinekeen.Models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by hughc on 2017-10-23.
 */

public class HabitList extends Observable {
    private ArrayList<Habit> habitList;

    public HabitList(){
        habitList = new ArrayList<Habit>();
    }
    public HabitList(ArrayList<Habit> habitList) {
        this.habitList = habitList;
    }

    public ArrayList<Habit> getHabits() {
        return habitList;
    }

    public void setHabitList(ArrayList<Habit> habitList) {
        this.habitList = habitList;
    }

    public void addHabit(Habit habit){
        habitList.add(habit);
        setChanged();
        notifyObservers(habitList);
    }

    //todo, make sure the habit title is unique, or find another way to
    public Habit getHabit(String type) { //return Habit from list by habit type/name

        for(Habit habit : habitList){
            if( habit.getHabitTitle().equals(type) ){
                setChanged();
                notifyObservers();
                return habit;
            }
        }
        return null;
    }

    public void removeHabit(String type){ //remove Habit from list by habit type/name
        for(Habit habit : habitList) {
            if( habit.getHabitTitle().equals(type) ) {
                habitList.remove(habit);
                setChanged();
                notifyObservers();
            }
        }
    }

    public int habitListSize(){
        return habitList.size();
    }
}
