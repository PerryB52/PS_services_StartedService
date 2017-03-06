package com.example.alexandrup.ps_services_startedservice.activities;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alexandrup.ps_services_startedservice.R;

/**
 * Created by alexandrup on 3/6/2017.
 */

public class MyMessengerActivity extends AppCompatActivity {

    boolean mIsBound = false;
    private TextView txvResult;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIsBound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        txvResult = (TextView) findViewById(R.id.txvResult);
    }

    public void performAddOperation(View view) {

        EditText etNumOne = (EditText) findViewById(R.id.etNumOne);
        EditText etNumTwo = (EditText) findViewById(R.id.etNumTwo);

        int num1 = Integer.valueOf(etNumOne.getText().toString());
        int num2 = Integer.valueOf(etNumTwo.getText().toString());
    }

    public void bindService(View view) {
    }

    public void unbindService(View view) {
    }
}
