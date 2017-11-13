package ca.ualberta.cs.routinekeen.Helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hughc on 2017-11-12.
 */

public class DateHelpers {
    // Taken from: https://stackoverflow.com/questions/20414343/
    // how-to-set-time-of-java-util-date-instance-to-000000
    // Date: Nov 12, 2017
    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date formatStringToDate(String dateString, String format){
        DateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try{
            date = sdf.parse(dateString);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDateToString(Date dateObj, String format){
        DateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dateObj);
    }
}
