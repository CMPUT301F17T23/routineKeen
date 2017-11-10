package ca.ualberta.cs.routinekeen.Controllers;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.routinekeen.Models.HabitList;

/**
 * Created by Saddog on 11/9/2017.
 */

public class UserDataController implements Observer{
    private HabitListController userHabitListController = new HabitListController();
    private HabitList userHabitList = userHabitListController.getHabitList();
    private static UserDataController userDataController = new UserDataController();
    public HabitList getUserHabitList(){
        return userHabitList;
    }

    @Override
    public void update(Observable observable, Object data) {
        System.out.println("some action was observed.");
    }
}
