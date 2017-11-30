package ca.ualberta.cs.routinekeen.Controllers;


import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;

/**
 * Created by Mikee V on 2017-11-08.
 */

public class HabitHistoryController{
    private HabitHistoryController(){}
    private static HabitHistory habitHistory = null;

    public static HabitHistory getHabitHistory(){
        if (habitHistory == null) {
            habitHistory = IOManager.getManager().loadHabitHistory();
        }
        return habitHistory;
    }

    public static void saveHabitHistory(){
        IOManager.getManager().saveHabitHistory(getHabitHistory());
    }

    public static void addHabitEvent(HabitEvent event){
        getHabitHistory().addHabitEvent(event);
        saveHabitHistory();
    }

    public static void removeHabitEvent(HabitEvent event){
        getHabitHistory().removeHabitEvent(event);
        saveHabitHistory();
    }

    public static HabitEvent getHabitEvent(int position)
    {
        return habitHistory.getHabitEvent(position);
    }



}
