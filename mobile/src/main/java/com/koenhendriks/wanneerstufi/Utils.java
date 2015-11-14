package com.koenhendriks.wanneerstufi;

import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Wanneer Stufi
 *
 * Created by koen on 10/7/15.
 */
public class Utils {
    public Calendar cal = Calendar.getInstance();

    public int payDay = 24;
    public int payMonth = cal.get(Calendar.MONTH)+1;
    public int payYear = cal.get(Calendar.YEAR);

    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    /**
     * Get the amount of days between two dates.
     *
     * @param sDate Date of the first date to calculate
     * @param eDate Date of the second date to calculate
     * @return integer of the amount of days between the two days
     */
    public int daysDifference(Date sDate, Date eDate){

        Calendar sCalendar = Calendar.getInstance();
        sCalendar.setTime(sDate);

        Calendar eCalendar = Calendar.getInstance();
        eCalendar.setTime(eDate);

        long milis1 = sCalendar.getTimeInMillis();
        long milis2 = eCalendar.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = Math.abs(milis2 - milis1);

        // Calculate difference in days
        return (int)(diff / (24 * 60 * 60 * 1000));
    }

    // TODO: put functionality of MainActivity inside methods
    public void nextPayDate(){

    }


}
