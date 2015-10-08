package com.koenhendriks.wanneerstufi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView text = (TextView) findViewById(R.id.date_untill);
        Utils utils = new Utils();

        Date sDate=null;
        Date eDate=null;
        Integer days = null;

        try {
            sDate = utils.dateFormat.parse("07/09/2015");
            eDate = utils.dateFormat.parse("17/09/2015");
            days = utils.daysDifference(sDate, eDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(sDate != null && eDate != null){
            text.setText("Nog "+days+" dagen");
        }else{
            text.setText("Er is iets misgegaan");
        }

        Calendar cal = Calendar.getInstance();
        Integer day = utils.payDate;
        Integer month = cal.get(Calendar.MONTH)+1; // January = 0, etc
        Integer year = cal.get(Calendar.YEAR);

        if(day > utils.payDate){
            month = month+1; // If we already passed payday we want to calculate for the next month.
        }

        if(month == 13){
            // If we passed the 12th month we need to calculate for the next year (January)
            month = 1;
            year = year+1;
        }

        Date payDay = null;

        try{
            payDay = utils.dateFormat.parse(""+day+"/"+month+"/"+year);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(payDay != null) {
            text.setText(payDay.toString());
        }



    }
}
