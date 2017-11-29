package ca.ualberta.cs.routinekeen.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.routinekeen.Helpers.DateHelpers;
import io.searchbox.annotations.JestId;

/**
 * Creates Habit objects associated with a specific userID that have Title, Reason, Date, and
 * an array of scheduled days
 *
 * @author  RoutineKeen
 * @see     HabitList
 * @see     ca.ualberta.cs.routinekeen.Views.HabitEditActivity
 * @version 1.0.0
 */

public class Habit implements Serializable {
    private String habitID;
    private String associatedUserID;
    private String habitTitle;
    private String habitReason;
    private Date startDate;
    private ArrayList<String> scheduledDays;

    public Habit() {
        this.habitTitle = "";
        this.habitReason = "";
        this.scheduledDays = new ArrayList<String>();
    }

    public Habit(String habitTitle, String habitReason, Date startDate) {
        this.habitTitle = habitTitle;
        this.habitReason = habitReason;
        this.startDate = startDate;
        this.scheduledDays = new ArrayList<String>();
    }

    public String getHabitID(){
        return this.habitID;
    }

    public void setHabitID(String id){
        this.habitID = id;
    }

    /**
     * Returns the id of the user to whom the habit belongs
     * @return  The user's ID
     * @see     User
     */
    public String getAssociatedUserID() {
        return associatedUserID;
    }

    /**
     * Sets the ID of the user who owns this habit
     * @param habitUserID   The user's ID
     * @see     User
     */
    public void setAssociatedUserID(String habitUserID) {
        this.associatedUserID = habitUserID;
    }

    /**
     * Returns the title of this habit
     * @return  This habit's title (String)
     */
    public String getHabitTitle() {
        return habitTitle;
    }

    /**
     * Sets the title of this habit to a provided string
     * @param habitTitle The new habit title
     */
    public void setHabitTitle(String habitTitle) {
        this.habitTitle = habitTitle;
    }

    /**
     * Returns this habit's reason
     * @return The reason (String)
     */
    public String getHabitReason() {
        return habitReason;
    }

    /**
     * Used to change this habit's reason
     * @param habitReason The new reason (String)
     */
    public void setHabitReason(String habitReason) {
        this.habitReason = habitReason;
    }

    /**
     * Returns the habit's start date
     * @return Start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the habits's start date
     * @param startDate The new start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns a list of the days this habit is scheduled for
     * @return A String ArrayList of days of the week (format "Wed")
     */
    public ArrayList<String> getScheduledHabitDays() {
        return scheduledDays;
    }

    /**
     * Used to update the habit's scheduled days
     * @param scheduledHabitDays The new schedule
     */
    public void setScheduledHabitDays(ArrayList<String> scheduledHabitDays) {
        this.scheduledDays = scheduledHabitDays;
    }

    /**
     * Overrides the toString function in this class.
     * Returns just the title if no reason is given and both otherwise.
     * @return habitTitle (habitReason)
     */
    public String toString() {
        String habitDate = DateHelpers.formatDateToString(getStartDate(), "MMMM dd, yyyy");
        if (getHabitReason() != null && !getHabitReason().isEmpty()) {
            if (getStartDate() != null) {
                return String.format(getHabitTitle() + " (" + getHabitReason()
                        + ")\n" + "Habit Start Date: " + habitDate);
            }
            else {
                return String.format(getHabitTitle() + " (" + getHabitReason()
                        + ")\n" + "Habit Start Date: " + habitDate);
            }
        }
        else if (getStartDate() != null) {
            return String.format(getHabitTitle() + "\n" + "Habit Start Date: " + habitDate);
        }
        else {
            return getHabitTitle();
        }
    }
}
