package ca.ualberta.cs.routinekeen.Models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Creates HabitEvents (instances of a user completing a habit) with an associated Date, location,
 * comment, photo, and habit
 *
 * @author  RoutineKeen
 * @see     HabitHistory
 * @see     HabitLocation
 * @see     ca.ualberta.cs.routinekeen.Views.HabitHistoryActivity
 * @version 1.0.0
 */

public class HabitEvent implements Comparable<HabitEvent>, Serializable {
    private String eventTitle;
    private Date eventDate;
    private HabitLocation eventLocation;
    private String habitEventUserID;
    private String eventComment;
    private Photo eventPhoto;
    private String eventHabitType; //todo in habitEvent activity, check if habitType exist before calling class constructor

    public HabitEvent(String title, String comment){
        this.eventTitle = title;
        this.eventComment = comment;
        this.eventDate = new Date();
        this.eventHabitType = "default habit type"; //// TODO: 11/13/2017  habit type checking for habitEvents
    }
//    public HabitEvent(String title, String habitType) {
//        this.title = title;
//        this.habitType = habitType;
//        this.comment = "";
//        this.date = new Date();
//   }

    public HabitEvent(String title,String habitType, String comment) {
        this.eventTitle = title;
        this.eventHabitType = habitType;
        this.eventComment = comment;
        this.eventDate = new Date();
    }

    public HabitEvent(String title, String habitType, String comment, HabitLocation location) {
        this.eventTitle = title;
        this.eventHabitType = habitType;
        this.eventLocation = location;
        this.eventComment = comment;
        this.eventDate = new Date();
    }

    public HabitEvent(String title, String habitType, String comment, HabitLocation location, Photo photo) {
        this.eventTitle = title;
        this.eventHabitType = habitType;
        this.eventLocation = location;
        this.eventComment = comment;
        this.eventPhoto = photo;
        this.eventDate = new Date();
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

    public HabitLocation getLocation() {
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

    public void setLocation(HabitLocation location) {
        this.eventLocation = location;
    }

    public void setComment(String comment) {
        this.eventComment = comment;
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
    public String toString()
    {
        return this.eventTitle + "       " + this.eventComment + "\n" + this.eventDate.toString();
    }
}
