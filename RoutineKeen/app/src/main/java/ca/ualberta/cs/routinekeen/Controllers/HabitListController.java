package ca.ualberta.cs.routinekeen.Controllers;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;

/**
 * Created by tiakindele on 2017-11-07.
 */

public class HabitListController {

    // Lazy Singleton
    private static HabitList habitList = null;
    static public HabitList getHabitList() {
        if (habitList == null) {
            habitList = new HabitList();
        }
        return habitList;
    }

    public void addHabit(Habit habit) {
        getHabitList().addHabit(habit);
    }
}
