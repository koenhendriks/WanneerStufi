package com.koenhendriks.wanneerstufi;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    Handler handler = new Handler();

    public NotificationService() {
        Log.w("", "Service Enabled!");

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.w("","loop test");

            }
        }, 0, 5000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
