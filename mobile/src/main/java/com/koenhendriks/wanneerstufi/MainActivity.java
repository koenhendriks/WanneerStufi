package com.koenhendriks.wanneerstufi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
        TextView textDate = (TextView) findViewById(R.id.date_placeholder);
        Utils utils = new Utils();
        Calendar cal = Calendar.getInstance();

        Integer day = cal.get(Calendar.DATE);
        Integer month = cal.get(Calendar.MONTH)+1; // January = 0, etc
        Integer year = cal.get(Calendar.YEAR);

        Date payDate = null;
        Date date = null;
        Integer days = null;


        if(day > utils.payDay){
            utils.payMonth = month+1; // If we already passed payday we want to calculate for the next month.

            if(utils.payMonth == 13){
                // If we passed the 12th month we need to calculate for the next year (January)
                utils.payMonth = 1;
                utils.payYear = year+1;
            }
        }

        try{
            date = utils.dateFormat.parse(""+day+"/"+month+"/"+year);
            payDate = utils.dateFormat.parse(""+utils.payDay+"/"+utils.payMonth+"/"+utils.payYear);
            days = utils.daysDifference(date, payDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String payDayName = new SimpleDateFormat("EEEE", Locale.getDefault()).format(payDate);
        String payMonthName = new SimpleDateFormat("MMMM", Locale.getDefault()).format(payDate);

        textDate.setText(payDayName+" "+utils.payDay+" "+" "+payMonthName);

        if(payDate != null && date != null) {
            switch (days){
                case 1:
                    text.setText("Nog "+days+" dag");
                    break;
                case 0:
                    text.setText("Vandaag!");
                    break;
                default:
                    text.setText("Nog "+days+" dagen");
                    break;
            }
        }else{
            text.setText("Invalid date");
        }
    }
}
