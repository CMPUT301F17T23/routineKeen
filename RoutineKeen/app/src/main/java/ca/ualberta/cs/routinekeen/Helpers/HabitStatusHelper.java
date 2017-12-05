package ca.ualberta.cs.routinekeen.Helpers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.User;

/**
 * Created by Mikee V on 2017-12-03.
 */

public class HabitStatusHelper {

    /*private void getProgress()
    {
        ArrayList<HabitEvent> filteredList = new ArrayList<>();
        for (HabitEvent event : HabitHistoryController.getHabitHistory().getEvents()) {
            if (event.getEventHabitType().equals(habit.getHabitTitle())) {
                filteredList.add(event);
            }
        }
    }
        HashMap<String,HabitEvent> filteredMap = new HashMap<>();

        for(HabitEvent event : filteredList){
            String date = new SimpleDateFormat("yyyyMMdd").format(event.getDate());
            if(!filteredMap.containsKey(date)){
                filteredMap.put(date,event);
            }
        }

        totalEventTextView.setText(Integer.toString(filteredList.size()));
        Date startDate = habit.getStartDate();
        Date curDate = new Date();
        ArrayList<Date> betweenDates = getBetweenDates(startDate, curDate);
        ArrayList<String> dates = habit.getScheduledHabitDays();
        Multimap<String, Date> dateMap = HashMultimap.create();
        for (Date date : betweenDates) {
            String key = new SimpleDateFormat("EE").format(date);
            dateMap.put(key, date);
        }

        for (String day : dates) {
            scheduleGoal += dateMap.get(day).size();
            for (Date date : dateMap.get(day)){
                String tempDate = new SimpleDateFormat("yyyyMMdd").format(date);
                for( String key : filteredMap.keySet()){
                    if ( tempDate.equals(key) ){
                        scheduleCompleted++;
                    }
                }
            }
    }*/


    private ArrayList<Date> getBetweenDates(Date startDate, Date curDate) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        ArrayList<Date> dates = new ArrayList<>();
        while (calendar.getTime().getTime() <= curDate.getTime()){
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE,1);
        }
        return dates;
    }


    private boolean sameDay1(String day, Date date) {
        String eventDate = new SimpleDateFormat("EE").format(date);
        return eventDate.equals(day);
    }

    private boolean sameDay2(Date day1, Date day2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(day1);
        cal2.setTime(day2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
