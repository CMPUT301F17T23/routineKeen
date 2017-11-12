package ca.ualberta.cs.routinekeen.Controllers;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitList;

/**
 * Created by tiakindele on 2017-11-07.
 */

public class HabitListController implements  Observer{

    public HabitListController(){}
    // Lazy Singleton
    private static HabitList habitList = null;
    public static HabitList getHabitList() {
        if (habitList == null) {
            habitList = IOManager.getManager().loadHabitList();
        }
        return habitList;
    }

    public void addObvToList(){
        getHabitList().addObserver(this);
    }

    public static void saveHabitList(){
        IOManager.getManager().saveHabitList(getHabitList());
    }

    @Override
    public void update(Observable observable, Object data) {
        if(observable == habitList){
            //todo do remote I/O as well
            saveHabitList();
        }
    }
}
