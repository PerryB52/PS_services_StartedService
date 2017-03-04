package com.example.alexandrup.ps_services_startedservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startStartedService(View view) {
        /*
        started servuce runs on UI Thread independent of calling component(activity in this case)
        May block the UI if executed for longer duration
        used to return single task that does not return anything
        Started service can receive data through an intent but cannot return data back
            u need to use BoundService or BroadcastReceiver to receive data back.
         */
        Intent intent = new Intent(MainActivity.this, MyStartedService.class);
        intent.putExtra("sleepTime", 10);
        startService(intent);
    }

    public void stopStartedService(View view) {
        Intent intent = new Intent(MainActivity.this, MyStartedService.class);
        stopService(intent);
    }
}
