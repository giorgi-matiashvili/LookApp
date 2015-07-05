package com.lookapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lookapp.R;

/**
 * Created by user on 05/07/2015.
 */
public class AdminBookingFragment extends CustomFragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin_booking, container, false);
        return rootView;
    }
}
