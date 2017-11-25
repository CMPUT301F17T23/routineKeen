package ca.ualberta.cs.routinekeen.Controllers;

import android.util.Log;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Calendar;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;

/**
 * Controller used to access and modify Habits in the current user's HabitList
 * @author  RoutineKeen
 * @see     IOManager
 * @see     HabitList
 * @version 1.0.0
 */
public class HabitListController{
//    private static ArrayList<String> typeList = null;
    private static HabitList habitList = null;
    private static IOManager ioManager = IOManager.getManager();
    private HabitListController(){}

    /**
     * Returns a list of all habits belonging to the current user.
     * @return  A HabitList containing all the user's habits
     * @see     HabitList
     */
    public static HabitList getHabitList() {
        if (habitList == null) {
            habitList = ioManager.loadHabitList();
        }
        return habitList;
    }

//    public static ArrayList getTypeList(){
//        if( habitList != null ){
//            if(typeList == null){
//                for (Habit habit : habitList.getHabits()){
//                    typeList = new ArrayList<String>();
//                    typeList.add(habit.getHabitTitle());
//                }
//            }
//        }
//        return typeList;
//    }

    /**
     * Returns a list of all habits belonging to the current user that are scheduled for the current
     * day of the week.
     * @return  A HabitList containing today's habits
     * @see     HabitList
     */
    public static HabitList getTodaysHabits() {
        String today = "";
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:   today = "Sun";  break;
            case Calendar.MONDAY:   today = "Mon";  break;
            case Calendar.TUESDAY:  today = "Tue";  break;
            case Calendar.WEDNESDAY:today = "Wed";  break;
            case Calendar.THURSDAY: today = "Thu";  break;
            case Calendar.FRIDAY:   today = "Fri";  break;
            case Calendar.SATURDAY: today = "Sat";  break;
            default:                today = null;   break;
        }
        /*
         Taken from: https://stackoverflow.com/questions/5574673/what-is-the-easiest-way-to-get-
         the-current-day-of-the-week-in-android
         Date: Nov 13, 2017
        */

        HabitList returnList = new HabitList();
        for (Habit x:getHabitList().getHabits()) {
            if (x.getScheduledHabitDays().contains(today)) {
                returnList.addHabit(x);
            }
        }

        return returnList;
    }

    /**
     * Adds the provided habit to the current user's HabitList
     * @param   habit The Habit to be added
     * @see     Habit
     * @see     HabitList
     */
    public static void addHabit(Habit habit){
        getHabitList().addHabit(habit);
        saveHabitList();
    }

    /**
     * Updates the habit edited and saves it to the network
     * and local storage
     * @param title The title of the updated/unchanged habit
     * @param reason The reason for the updated/unchanged habit
     * @param schedDays The scheduled days for the updated/unchanged habit
     * @param position The position of the habit within the habit list
     */
    public static void updateHabit(String title, String reason,
                                   ArrayList<String> schedDays, int position){
        // TODO write code to update habit on network
        getHabitList().updateHabit(title, reason, schedDays, position);
        saveHabitList();
    }

    /**
     * Saves changed to the user's HabitList. Called after additions, deletions, or edits are made
     * to the HabitList
     * @see     IOManager
     * @see     HabitList
     */
    public static void saveHabitList(){
        ioManager.saveHabitList(getHabitList());
    }
}
