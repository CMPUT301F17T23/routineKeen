package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Booleans;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.Models.Habit;
import ca.ualberta.cs.routinekeen.Models.HabitEvent;
import ca.ualberta.cs.routinekeen.Models.HabitList;
import ca.ualberta.cs.routinekeen.R;


/**
 * Created by Saddog on 11/30/2017.
 */

public class HabitProgressActivity extends AppCompatActivity {
    private TextView habitTypeTextView,totalEventTextView;
    private long dateRange;
    private Integer scheduleGoal = 0, scheduleCompleted = 0;
    private ProgressBar scheduledProgressBar;
    private Button doneButton;
    private TextView scheduledProgress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_progress);
        IOManager.initManager(getApplicationContext());
        scheduledProgressBar = (ProgressBar) findViewById(R.id.scheduledProgressBar);
        scheduledProgress = (TextView) findViewById(R.id.scheduledProgressTextView);
        habitTypeTextView = (TextView) findViewById(R.id.habitTypeTextView);
        totalEventTextView = (TextView) findViewById(R.id.totalEventsCount);
        Bundle data = getIntent().getExtras();
        Bundle habitData = data.getBundle("habit");
        Habit habit = HabitListController.getHabitList().getHabitByPosition(habitData.getInt("position"));
        String string = "For your habit " + habit.getHabitTitle() + ", your current progress is:";
        habitTypeTextView.setText(string);

        ArrayList<HabitEvent> filteredList = new ArrayList<>();
        for (HabitEvent event : HabitHistoryController.getHabitHistory().getEvents()) {
            if (event.getEventHabitType().equals(habit.getHabitTitle())) {
                filteredList.add(event);
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

                double x = (double) scheduleCompleted / (double) scheduleGoal;
                scheduledProgressBar.setProgress((int) (x * 100));
        }

        scheduledProgress.setText(Integer.toString(scheduleCompleted) + " day(s) out of the "
                + Integer.toString(scheduleGoal) + " day(s) scheduled");
    }


    //get a list of dates between two java dates
    //https://stackoverflow.com/questions/2689379/how-to-get-a-list-of-dates-between-two-dates-in-java
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
