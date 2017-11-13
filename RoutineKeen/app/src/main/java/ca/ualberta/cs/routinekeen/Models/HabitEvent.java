package ca.ualberta.cs.routinekeen.Models;

import android.support.annotation.NonNull;

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

public class HabitEvent implements Comparable<HabitEvent> {
    private String title;
    private Date date;
    private HabitLocation location;
    private String comment;
    private Photo photo;
    private String habitType; //todo in habitEvent activity, check if habitType exist before calling class constructor

    public HabitEvent(String title, String habitType) {
        this.title = title;
        this.habitType = habitType;
        this.date = new Date();
    }

    public HabitEvent(String title,String habitType, String comment) {
        this.title = title;
        this.habitType = habitType;
        this.comment = comment;
        this.date = new Date();
    }

    public HabitEvent(String title, String habitType, String comment, HabitLocation location) {
        this.title = title;
        this.habitType = habitType;
        this.location = location;
        this.comment = comment;
        this.date = new Date();
    }

    public HabitEvent(String title, String habitType, String comment, HabitLocation location, Photo photo) {
        this.title = title;
        this.habitType = habitType;
        this.location = location;
        this.comment = comment;
        this.photo = photo;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public HabitLocation getLocation() {
        return location;
    }

    public String getComment() {
        return comment;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(HabitLocation location) {
        this.location = location;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public void deleteImage() {
        this.photo = null;
    }

    @Override
    public int compareTo(@NonNull HabitEvent compareEvent) {
        int currentDate = (int)(date.getTime()/1000);
        int compareDate = (int)(compareEvent.getDate().getTime()/1000);
        return compareDate - currentDate;
    }
}
