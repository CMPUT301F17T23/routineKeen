package ca.ualberta.cs.routinekeen.Controllers;

import java.util.ArrayList;
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

    public static void addHabit(Habit habit){
        getHabitList().addHabit(habit);
        saveHabitList();
    }

    public static void saveHabitList(){
        ioManager.saveHabitList(getHabitList());
    }
}
