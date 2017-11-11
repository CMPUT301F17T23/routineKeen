package ca.ualberta.cs.routinekeen.Models;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hughc on 2017-10-23.
 */

public class HabitHistory {
    private ArrayList<HabitEvent> habitEvents;
    private String currentHabitTypeFilter;
    private String currentHabitCommentFilter;

    public HabitHistory(){
        this.habitEvents = new ArrayList<HabitEvent>();
    }

    public HabitHistory(ArrayList<HabitEvent> habitEvents) {
        this.habitEvents = habitEvents;
    }

    public void addHabitEvent(HabitEvent habitEvent){
        this.habitEvents.add(habitEvent);
    }

    public int habitHistorySize(){
        return habitEvents.size();
    }

    public HabitEvent getHabitEventByName(String name) {
        return null; // implement
    }

    public void removeHabitEventByName(String name) {}

    //Added my mikeev
    public HabitEvent getHabitEventByIndex(int index)
    {
        return this.habitEvents.get(index);
    }
    public void removeHabitEventByIndex(int index)
    {
        this.habitEvents.remove(index);
    }

    public Collection<HabitEvent> getFilteredList() {
        return null;
    }

    public String getCurrentHabitTypeFilter() {
        return currentHabitTypeFilter;
    }

    public void setCurrentHabitTypeFilter(String currentHabitTypeFilter) {
        this.currentHabitTypeFilter = currentHabitTypeFilter;
    }

    public String getCurrentHabitCommentFilter() {
        return currentHabitCommentFilter;
    }

    public void setCurrentHabitCommentFilter(String currentHabitCommentFilter) {
        this.currentHabitCommentFilter = currentHabitCommentFilter;
    }
}
