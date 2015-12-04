package com.koenhendriks.wanneerstufi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    public Integer days = null;
    public Context ctx = this;
    public Integer notificationId = 1337;
    public Integer send = 0;


    public NotificationService() {
        Log.w("", "Service Enabled!");

        new Timer().scheduleAtFixedRate(new TimerTask() {

            /**
             * Send a notification with the days left to the paydate
             *
             * @param message
             */
            public void notifyDays(String message){
                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                                .setSmallIcon(R.drawable.ic_attach_money_white_24dp)
                                .setContentTitle("Wanneer Stufi?")
                                .setContentText(message);

                mBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND).setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
                ;

                Intent resultIntent = new Intent(ctx, MainActivity.class);

                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(MainActivity.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                mBuilder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                if(!Objects.equals(send.toString(), days.toString())){
                    mNotificationManager.notify(notificationId, mBuilder.build());
                    send = days;
                }

            }

            @Override
            public void run() {
                Log.w("", "notification loop");

                Utils utils = new Utils();
                Calendar cal = Calendar.getInstance();

                Integer day = cal.get(Calendar.DATE);
                Integer month = cal.get(Calendar.MONTH)+1; // January = 0, etc
                Integer year = cal.get(Calendar.YEAR);

                Date payDate = null;
                Date date = null;


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

                String nextDateString = payDayName+" "+utils.payDay+" "+" "+payMonthName;

                if(payDate != null && date != null) {

                    Log.w("","uur: "+Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    Log.w("", "minuut: " + Calendar.getInstance().get(Calendar.MINUTE));

                    if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 13 ) {

                        if(days == 10 || days == 3){
                            this.notifyDays("Nog " + days.toString() + " dagen!");
                        }else if(days == 1){
                            this.notifyDays("Morgen is het zover!");
                        }else if (days == 0){
                            this.notifyDays("Vandaag krijg je stufi!");
                        }
                    }
                }
            }
        }, 0, 30000);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
