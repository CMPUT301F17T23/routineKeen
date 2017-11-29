package ca.ualberta.cs.routinekeen.Models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.LinkedTransferQueue;

import com.google.android.gms.maps.model.LatLng;

import io.searchbox.annotations.JestId;

/**
 * Creates HabitEvents (instances of a user completing a habit) with an associated Date, location,
 * comment, photo, and habit
 *
 * @author  RoutineKeen
 * @see     HabitHistory
 * @see     ca.ualberta.cs.routinekeen.Views.HabitHistoryActivity
 * @version 1.0.0
 */

public class HabitEvent implements Comparable<HabitEvent>, Serializable {
    private String eventID;
    private String eventTitle;
    private Date eventDate;
    private LatLng eventLocation;
    private String associatedUserID;
    private String eventComment;
    private Photo eventPhoto;
    private String eventHabitType; //todo in habitEvent activity, check if habitType exist before calling class constructor

    public HabitEvent(String title, String comment){
        this.eventTitle = title;
        this.eventComment = comment;
        this.eventDate = new Date();
        this.eventHabitType = "default habit type"; //// TODO: 11/13/2017  habit type checking for habitEvents
    }

    public HabitEvent(String title,String habitType, String comment) {
        this.eventTitle = title;
        this.eventHabitType = habitType;
        this.eventComment = comment;
        this.eventDate = new Date();
    }

    public HabitEvent(String title, String habitType, String comment, LatLng location) {
        this.eventTitle = title;
        this.eventHabitType = habitType;
        this.eventLocation = location;
        this.eventComment = comment;
        this.eventDate = new Date();
    }

    public HabitEvent(String title, String habitType, String comment, LatLng location, Photo photo) {
        this.eventTitle = title;
        this.eventHabitType = habitType;
        this.eventLocation = location;
        this.eventComment = comment;
        this.eventPhoto = photo;
        this.eventDate = new Date();
    }

    public String getEventID(){
        return this.eventID;
    }

    public void setEventID(String id){
        this.eventID = id;
    }

    public Date getDate() {
        return eventDate;
    }

    public void setDate(Date date) {
        this.eventDate = date;
    }

    public String getTitle() {
        return eventTitle;
    }

    public String getAssociatedUserID() { return associatedUserID; }

    public LatLng getLocation() {
        return eventLocation;
    }

    public String getComment() {
        return eventComment;
    }

    public Photo getPhoto() {
        return eventPhoto;
    }

    public void setTitle(String title) {
        this.eventTitle = title;
    }

    public void setAssociatedUserID(String userID) {
        this.associatedUserID = userID;
    }

    public void setLocation(LatLng location) {
        this.eventLocation = location;
    }

    public void setComment(String comment) {
        this.eventComment = comment;
    }


    public void setEventHabitType(String eventHabitType) {
        this.eventHabitType = eventHabitType;
    }

    public void setPhoto(Photo photo) {
        this.eventPhoto = photo;
    }

    public void deleteImage() {
        this.eventPhoto = null;
    }

    public int compareTo(@NonNull HabitEvent compareEvent) {
        int currentDate = (int)(eventDate.getTime()/1000);
        int compareDate = (int)(compareEvent.getDate().getTime()/1000);
        return compareDate - currentDate;
    }

    @Override
    public String toString() {
        return  eventHabitType + "\n" + this.eventTitle + "\n" + this.eventComment + "\n" + this.eventDate.toString();
    }
}
