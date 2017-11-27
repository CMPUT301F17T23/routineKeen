package ca.ualberta.cs.routinekeen.Models;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;

import static android.widget.Toast.LENGTH_LONG;

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

    public Habit getHabitByPosition(int position){
        return habitList.get(position);
    }

    public Habit getHabitByType(String type) {
        //todo, make sure the habit title is unique, or find another way to
        for(Habit habit : habitList){
            if( habit.getHabitTitle().equals(type) ){
                setChanged();
                notifyObservers();
                return habit;
            }
        }
        return null;
    }

    public void removeHabitByPosition(int position){
        habitList.remove(position);
        setChanged();
        notifyObservers(habitList);
    }

    public void removeHabitByType(String type){ //remove Habit from list by habit type/name
        for(Habit habit : habitList) {
            if( habit.getHabitTitle().equals(type) ) {
                habitList.remove(habit);
                setChanged();
                notifyObservers();
                return;
            }
        }
    }

    public void updateHabit(String title, String reason,
                            ArrayList<String> schedDays, int position){
        habitList.get(position).setHabitTitle(title);
        habitList.get(position).setHabitReason(reason);
        habitList.get(position).setScheduledHabitDays(schedDays);
        setChanged();
        notifyObservers(habitList);
    }

    public int habitListSize(){
        return habitList.size();
    }
}
