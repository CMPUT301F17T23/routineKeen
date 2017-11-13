package ca.ualberta.cs.routinekeen.Controllers;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.Photo;
/**
 * Created by Mikee V on 2017-11-08.
 */

public class HabitHistoryController{// implements Observer{
    private HabitHistoryController(){}
    private static HabitHistory habitHistory = null;

    public static HabitHistory getHabitHistory(){
        if (habitHistory == null) {
            habitHistory = IOManager.getManager().loadHabitHistory();
        }
        return habitHistory;
    }

//    public void addObvToHistory(){
//        getHabitHistory().addObserver(this);
//    }

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

    //// TODO: 11/12/2017  add any static method that would act on the habitHistory singleton in anyway below
//    @Override
//    public void update(Observable observable, Object data) {
////        if(observable == habitHistory){
//            //// TODO: 11/11/2017 do remove I/O as well
//            saveHabitHistory();
////        }

//    }

/*
    public HabitEvent getHabitEvent(int position)
    {
        return habitHistory.getHabitEvent(position);
    }

    public void setHabitEvent(HabitEvent habitEvent)
    {
        habitHistory.addHabitEvent(habitEvent);
    }
*/

}
