package com.lookapp.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.lookapp.App;
import com.lookapp.activities.CustomActivity;
import com.lookapp.utils.AppLogger;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class CustomFragment extends Fragment {

    protected App app;
    protected AppLogger logger;
    protected CustomActivity activity;
    protected ActionBar actionBar;
    protected View backButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getActivity().getApplicationContext();
        activity = (CustomActivity) getActivity();
        logger = AppLogger.getLogger(this.getClass());
    }

}
