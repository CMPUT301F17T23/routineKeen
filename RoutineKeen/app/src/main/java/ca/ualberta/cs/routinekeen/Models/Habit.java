package ca.ualberta.cs.routinekeen.Models;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.routinekeen.Helpers.DateHelpers;

/**
 * Creates Habit objects associated with a specific userID that have Title, Reason, Date, and
 * an array of scheduled days
 *
 * @author  RoutineKeen
 * @see     HabitList
 * @see     ca.ualberta.cs.routinekeen.Views.HabitDetailsActivity
 * @see     ca.ualberta.cs.routinekeen.Views.HabitEditActivity
 * @version 1.0.0
 */

public class Habit {

    private String habitUserID;
    private String habitTitle;
    private String habitReason;
    private Date startDate;
    private ArrayList<String> scheduledHabitDays;

    public Habit() {
        this.habitTitle = "";
        this.habitReason = "";
    }

    public Habit(String habitTitle, String habitReason, Date startDate) {
        this.habitTitle = habitTitle;
        this.habitReason = habitReason;
        this.startDate = startDate;
    }

    public String getHabitUserID() {
        return habitUserID;
    }

    public void setHabitUserID(String habitUserID) {
        this.habitUserID = habitUserID;
    }

    public String getHabitTitle() {
        return habitTitle;
    }

    public void setHabitTitle(String habitTitle) {
        this.habitTitle = habitTitle;
    }

    public String getHabitReason() {
        return habitReason;
    }

    public void setHabitReason(String habitReason) {
        this.habitReason = habitReason;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ArrayList<String> getScheduledHabitDays() {
        return scheduledHabitDays;
    }

    public void setScheduledHabitDays(ArrayList<String> scheduledHabitDays) {
        this.scheduledHabitDays = scheduledHabitDays;
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
