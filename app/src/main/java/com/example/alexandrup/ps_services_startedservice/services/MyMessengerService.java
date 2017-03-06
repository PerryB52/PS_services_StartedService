package com.example.alexandrup.ps_services_startedservice.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by alexandrup on 3/6/2017.
 */

public class MyMessengerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int addNumbers(int a, int b){
        return a + b;
    }
}
