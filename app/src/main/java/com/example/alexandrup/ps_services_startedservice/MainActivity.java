package com.example.alexandrup.ps_services_startedservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txvIntentServiceResult, txvStartedServiceResult;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvIntentServiceResult = (TextView) findViewById(R.id.txvIntentServiceResult);
        txvStartedServiceResult = (TextView) findViewById(R.id.txvStartedServiceResult);
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

    public void startIntentService(View view) {

        ResultReceiver myResultReceiver = new MyResultReceiver(null);

        Intent intent = new Intent(MainActivity.this, MyIntentService.class);
        intent.putExtra("sleepTime", 3);
        intent.putExtra("receiver", myResultReceiver);
        startService(intent);

    }

    private class MyResultReceiver extends ResultReceiver {

        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            Log.i("MyResultreceiver", Thread.currentThread().getName());

            if (resultCode == 18 && resultData != null) {

                final String result = resultData.getString("resultIntentService");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("MyHandler", Thread.currentThread().getName());
                        txvIntentServiceResult.setText(result);
                    }
                });
            }
        }
    }


}
