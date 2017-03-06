package com.example.alexandrup.ps_services_startedservice.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alexandrup.ps_services_startedservice.services.MyBoundService;
import com.example.alexandrup.ps_services_startedservice.R;

/**
 * Created by alexandrup on 3/5/2017.
 */

public class MyBoundActivity extends AppCompatActivity {

    public static final String TAG = MyBoundActivity.class.getSimpleName();

    boolean isBound = false;
    private MyBoundService myBoundService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            MyBoundService.MyLocalBinder myLocalBinder = (MyBoundService.MyLocalBinder) iBinder;
            myBoundService = myLocalBinder.getService();

            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //bound service is started in onStart
        Intent intent = new Intent(this, MyBoundService.class);
        /* varius flags exist, this flag automatically creates the service as long as
            components are bound to it.
            BIND_DEBUG_UNBIND - creates debug information for nmatched bind/unbind calls
            BIND_NOT_FOREGROUND - bound service will never be brought to foreground process level
            0 - non of the above
            many other...
         */
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(isBound){
            unbindService(mConnection);
            isBound = false;
        }
    }

    public void onClickEvent(View view) {

        EditText etNumOne = (EditText) findViewById(R.id.etNumOne);
        EditText etNumTwo = (EditText) findViewById(R.id.etNumTwo);
        TextView txvResult = (TextView) findViewById(R.id.txvResult);

        int numOne = Integer.valueOf(etNumOne.getText().toString());
        int numTwo = Integer.valueOf(etNumTwo.getText().toString());

        String resultStr = "";

        if(isBound){

            switch (view.getId()){

                case R.id.btnAdd:
                        resultStr = String.valueOf(myBoundService.add(numOne, numTwo));
                    break;

                case R.id.btnSub:
                        resultStr = String.valueOf(myBoundService.subtract(numOne, numTwo));
                    break;

                case R.id.btnMul:
                        resultStr = String.valueOf(myBoundService.multiply(numOne, numTwo));
                    break;

                case R.id.btnDiv:
                        resultStr = String.valueOf(myBoundService.divide(numOne, numTwo));
                    break;

            }

            txvResult.setText(resultStr);
        }
    }
}
