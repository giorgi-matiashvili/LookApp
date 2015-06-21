package com.lookapp.activities;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.lookapp.App;
import com.lookapp.fragments.CustomFragment;
import com.lookapp.utils.AppLogger;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class CustomActivity extends Activity{

    protected AppLogger logger;
    protected Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        logger = AppLogger.getLogger(this.getClass());
        app = (App) getApplicationContext();

    }

    public void registerLocalBroadcastReceiver(String action, BroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(action));
    }

    public void unregisterLocalBroadcastReceiver(BroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }



}
