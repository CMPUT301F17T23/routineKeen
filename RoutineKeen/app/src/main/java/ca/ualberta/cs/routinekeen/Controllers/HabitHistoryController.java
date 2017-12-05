package ca.ualberta.cs.routinekeen.Controllers;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.Queue;

import ca.ualberta.cs.routinekeen.Exceptions.NetworkUnavailableException;
import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;

public class HabitHistoryController {
    private HabitHistoryController(){}
    private static HabitHistory habitHistory = null;
    private static IOManager ioManager = IOManager.getManager();
    private static Queue<String> offlineDeletedEventIds = new LinkedList<>();
    private static Queue<HabitEvent> offlineUpdatedEvents = new LinkedList<>();
    private static Queue<HabitEvent> offlineAddedEvents = new LinkedList<>();

    public static void initHabitHistory() {
            String userID = UserSingleton.getCurrentUser().getUserID();
            habitHistory = ioManager.loadUserHabitHistory(userID);
    }

    public static HabitHistory getHabitHistory(){
        if (habitHistory == null) {
            initHabitHistory();
        }
        return habitHistory;
    }

    public static void addHabitEvent(HabitEvent event){
        String userID = UserSingleton.getCurrentUser().getUserID();
        event.setAssociatedUserID(userID);
        String assignedEventID = null;
        try{
            assignedEventID = ioManager.addHabitEvent(event);
        } catch (NetworkUnavailableException e){
            // mark the event to be added/updated to elastic search
            // upon reconnection with an available network
            offlineAddedEvents.add(event);
        }
        event.setEventID(assignedEventID);
        getHabitHistory().addHabitEvent(event);
        ioManager.saveHabitHistory(getHabitHistory());
    }

    public static void removeHabitEvent(HabitEvent event){
        try{
            ioManager.deleteHabitEvent(event.getEventID());
        } catch (NetworkUnavailableException e){
            // enqueue the delete event for execution when connection available
            offlineDeletedEventIds.add(event.getEventID());
        }
        getHabitHistory().removeHabitEvent(event);
        ioManager.saveHabitHistory(getHabitHistory());
    }

    public static void updateHabitEvent(String title, String comment, String habitType,
                                        byte[] photo, LatLng location, int position){
        HabitEvent habitToUpdate = getHabitHistory().getHabitEvent(position);
        habitToUpdate.setTitle(title);
        habitToUpdate.setComment(comment);
        habitToUpdate.setLocation(location);
        habitToUpdate.setPhoto(photo);
        habitToUpdate.setEventHabitType(habitType);
        try{
            ioManager.updateHabitEvent(habitToUpdate);
        } catch(NetworkUnavailableException e) {
            offlineUpdatedEvents.add(habitToUpdate);
        }
        getHabitHistory().updateHabitEvent(title, comment, habitType,
                photo, location, position);
        saveHabitHistory();
    }

    public static HabitEvent getHabitEvent(int position)
    {
        return habitHistory.getHabitEvent(position);
    }

    public static void executeOfflineQueuedRequests(){
        int addQueueSize = offlineAddedEvents.size();
        for(int i = 0; i < addQueueSize; i++){
            HabitEvent eventToAdd = offlineAddedEvents.peek();
            try{
                ioManager.addHabitEvent(eventToAdd);
            } catch(NetworkUnavailableException e){
                continue;
            }
            offlineAddedEvents.poll();
        }

        int updateQueueSize = offlineUpdatedEvents.size();
        for(int i = 0; i < updateQueueSize; i++){
            HabitEvent eventToUpdate = offlineUpdatedEvents.peek();
            try{
                ioManager.updateHabitEvent(eventToUpdate);
            } catch(NetworkUnavailableException e){
                continue;
            }
            offlineUpdatedEvents.poll();
        }

        for(int i = 0; i < offlineDeletedEventIds.size(); i++){
            String eventID = offlineDeletedEventIds.peek();
            try{
                ioManager.deleteHabitEvent(eventID);
            } catch (NetworkUnavailableException e){
                continue;
            }
            offlineDeletedEventIds.poll();
        }
    }

    public static void saveHabitHistory(){
        ioManager.saveHabitHistory(habitHistory);
    }

    public static void clearController(){
        habitHistory = null;
    }
}
