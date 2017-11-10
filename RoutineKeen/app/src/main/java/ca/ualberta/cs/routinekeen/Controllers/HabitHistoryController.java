package ca.ualberta.cs.routinekeen.Controllers;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Models.HabitHistory;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.Photo;
/**
 * Created by Mikee V on 2017-11-08.
 */

public class HabitHistoryController {
    private ArrayList<HabitEvent> habitHistory = new ArrayList<HabitEvent>();


    public HabitEvent getHabitEvent(int position)
    {
        return habitHistory.get(position);
    }

    public void setHabitEvent(HabitEvent habitEvent)
    {
        habitHistory.add(habitEvent);
    }


}
