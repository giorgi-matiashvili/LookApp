package com.lookapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lookapp.R;
import com.lookapp.api.RequestInvoker;


/**
 * Created by Giorgi on 6/20/2015.
 */
public class StartupActivity extends CustomActivity{


    private BroadcastReceiver startupListner = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            startActivity(new Intent(StartupActivity.this, UnauthorizedActivity.class));
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    unregisterReceiver();
                    StartupActivity.this.finish();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        registerReceiver();
    }

    private void unregisterReceiver() {
        unregisterLocalBroadcastReceiver(startupListner);
    }

    private void registerReceiver() {
        registerLocalBroadcastReceiver(RequestInvoker.ACTION_STARTUP_COMPLETED, startupListner);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        RequestInvoker.getInstance().onStartup();
    }
}
