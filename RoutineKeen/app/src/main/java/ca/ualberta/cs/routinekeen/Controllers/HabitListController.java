package ca.ualberta.cs.routinekeen.Controllers;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;

/**
 * Created by tiakindele on 2017-11-07.
 */

public class HabitListController {

    private HabitListController(){}
    // Lazy Singleton
    private static HabitList habitList = null;
    public static HabitList getHabitList() {
        if (habitList == null) {
            habitList = new HabitList();
        }
        return habitList;
    }

    public static void saveHabitList(){
        IOManager iom = IOManager.getManager();
        iom.local
    }

    public static void addHabit(Habit habit) {
        getHabitList().addHabit(habit);
    }

}
