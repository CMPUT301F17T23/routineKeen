package ca.ualberta.cs.routinekeen.Controllers;

import android.util.Log;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;

/**
 * Created by tiakindele on 2017-11-07.
 */

public class HabitListController{
    private static HabitList habitList = null;
    private static IOManager ioManager = IOManager.getManager();

    private HabitListController(){}
    public static HabitList getHabitList() {
        if (habitList == null) {
            habitList = ioManager.loadHabitList();
        }
        return habitList;
    }

    public static HabitList getTodaysHabits() {
        Log.d("Schedule-Con", "flag1");
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
        Log.d("Schedule-Con", "flag2");
        /*
         Taken from: https://stackoverflow.com/questions/5574673/what-is-the-easiest-way-to-get-the-current-day-of-the-week-in-android
         Nov 13, 2017
        */
        if (today == null) {
            Log.d("HLControllerCheck", "Today is null");
        }
        Log.d("Schedule-Con", "flag3");
        HabitList returnList = new HabitList();
        Log.d("Schedule-Con", "flag31");
        for (Habit x:getHabitList().getHabits()) {
            if (x.getScheduledHabitDays().contains(today)) {
                returnList.addHabit(x);
            }
            Log.d("Schedule-Con", "flag32");
        }
        Log.d("Schedule-Con", "flag4");

        return returnList;
    }

    public static void addHabit(Habit habit){
        getHabitList().addHabit(habit);
        saveHabitList();
    }

    public static void saveHabitList(){
        ioManager.saveHabitList(getHabitList());
    }
}
