package com.example.alexandrup.ps_services_startedservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by alexandrup on 3/3/2017.
 */

public class MyStartedService extends Service {

            /*
        started servuce runs on UI Thread independent of calling component(activity in this case)
        May block the UI if executed for longer duration
        used to return single task that does not return anything
        Started service can receive data through an intent but cannot return data back
            u need to use BoundService or BroadcastReceiver to receive data back.
         */

    public static final String TAG = MyStartedService.class.getSimpleName();

    @Override
    public void onCreate() {

        Log.i(TAG, "onCreate, Thread name " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand, Thread name " + Thread.currentThread().getName());

        //perform test dummy long running task
        int sleepTime = intent.getIntExtra("sleepTime", 1);
//        try {
//            Thread.sleep(sleepTime*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        new MyAsyncTask().execute(sleepTime);


        return START_STICKY; //or START_REDELIVER_INTENT etc --> this sets behavior for what to do when sservice is destroyed
        //return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.i(TAG, "onBind, Thread name " + Thread.currentThread().getName());
        //if service is started service always return null
        return null;
    }

    @Override
    public void onDestroy() {

        Log.i(TAG, "onDestroy, Thread name " + Thread.currentThread().getName());
        super.onDestroy();
    }

    class MyAsyncTask extends AsyncTask<Integer, String, Void> {

        public final String TAG = MyAsyncTask.class.getSimpleName();


        @Override
        protected void onPreExecute() {

            Log.i(TAG, "onPreExecute, Thread name " + Thread.currentThread().getName());
            super.onPreExecute();
        }

        @Override //perform long running task here - works on different thread
        protected Void doInBackground(Integer... params) {
            Log.i(TAG, "doInBackground, Thread name " + Thread.currentThread().getName());

            int sleepTime = params[0];

            int ctr = 1;

            while (ctr <= sleepTime) {
                publishProgress("Counter is now " + ctr); //publish progress triggers onProgressUpdate

                try {
                    Thread.sleep(sleepTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctr++;
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            Toast.makeText(MyStartedService.this, values[0], Toast.LENGTH_SHORT).show();
            Log.i(TAG, "onProgressUpdate, Thread name " + Thread.currentThread().getName());


        }

        @Override
        protected void onPostExecute(Void aVoid) {

            stopSelf(); //stops service by itself
            Log.i(TAG, "onPostExecute, Thread name " + Thread.currentThread().getName());
            super.onPostExecute(aVoid);
        }
    }
}
