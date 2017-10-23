package ca.ualberta.cs.routinekeen.Models;

import java.util.Date;

/**
 * Created by hughc on 2017-10-23.
 */

public class HabitEvent {
    String title;
    Date date;
    HabitLocation location;
    String comment;
    Photo photo;

    public HabitEvent(String title) {
        this.title = title;
        this.date = new Date();
    }

    public HabitEvent(String title, String comment) {
        this.title = title;
        this.comment = comment;
    }

    public HabitEvent(String title, String comment, HabitLocation location) {
        this.title = title;
        this.location = location;
        this.comment = comment;
    }

    public HabitEvent(String title, String comment, HabitLocation location, Photo photo) {
        this.title = title;
        this.location = location;
        this.comment = comment;
        this.photo = photo;
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
}
