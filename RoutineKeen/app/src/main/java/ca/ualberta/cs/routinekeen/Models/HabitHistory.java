package ca.ualberta.cs.routinekeen.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Observable;

/**
 * Created by hughc on 2017-10-23.
 */

public class HabitHistory extends Observable{
    private ArrayList<HabitEvent> habitHistory;

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
        setChanged();
        notifyObservers();

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
        Collections.sort(filterList);
        return filterList;
    }

    public void removeHabitEvent(HabitEvent eventToRemove)
    {
        habitHistory.remove(eventToRemove);
        Collections.sort(habitHistory);
        setChanged();
        notifyObservers();
    }

    public HabitEvent getHabitEventsFilteredByComment(String comment){
        for (HabitEvent events : habitHistory){
            if ( events.getComment().contains(comment) ){
                return events;
            }
        }
        return null;
    }

    public HabitEvent getHabitEvent(int position)
    {
        setChanged();
        notifyObservers();
        return habitHistory.get(position);
    }

    public Collection<HabitEvent> getEvents() {
        return habitHistory;
    }
}
