package ca.ualberta.cs.routinekeen.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by hughc on 2017-10-23.
 */

public class HabitHistory {
    private ArrayList<HabitEvent> habitHistory;
    private String currentHabitTypeFilter;
    private String currentHabitCommentFilter;

    public HabitHistory(){
        this.habitHistory = new ArrayList<HabitEvent>();
    }

    public HabitHistory(ArrayList<HabitEvent> habitHistory) {
        this.habitHistory = habitHistory;
        Collections.sort(habitHistory);
    }

    public void addHabitEvent(HabitEvent habitEvent) {
        this.habitHistory.add(habitEvent);
        Collections.sort(habitHistory);
    }

    public int getSize(){
        return habitHistory.size();
    }

    public ArrayList<HabitEvent> getHabitEventsByType(String type) {
        ArrayList<HabitEvent> filterList = new ArrayList<HabitEvent>();
        for(HabitEvent event : habitHistory){
            if ( event.getTitle() == type ){
                filterList.add(event);
            }
        }
        return filterList;
    }

    public void removeHabitEvent(HabitEvent eventToRemove) {
        habitHistory.remove(eventToRemove);
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
