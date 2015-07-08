package com.lookapp.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.lookapp.App;
import com.lookapp.R;
import com.lookapp.fragments.CustomFragment;
import com.lookapp.utils.AppLogger;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class CustomActivity extends Activity{

    protected AppLogger logger;
    protected App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        logger = AppLogger.getLogger(this.getClass());
        app = (App) getApplicationContext();

    }

    public ActionBar getCustomLayoutActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            if (actionBar.getCustomView() == null) {
                actionBar.setCustomView(R.layout.actionbar_custom_view);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayShowCustomEnabled(true);
            }
        return actionBar;
    }

    public void showActionBarToggle(ActionBar actionBar, boolean show) {
        View actionBarToggle = actionBar.getCustomView().findViewById(R.id.custom_action_bar_toggle);
        if (show) {
            actionBarToggle.setVisibility(View.VISIBLE);
        } else {
            actionBarToggle.setVisibility(View.GONE);
        }
    }

    public void registerLocalBroadcastReceiver(String action, BroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(action));
    }

    public void unregisterLocalBroadcastReceiver(BroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }



}
