package com.lookapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lookapp.R;
import com.lookapp.activities.RegisterActivity;

/**
 * Created by user on 02/07/2015.
 */
public class LoginFragment extends CustomFragment implements View.OnClickListener{
    private View rootView;
    private TextView loginRegister;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        loginRegister = (TextView)rootView.findViewById(R.id.login_register);
        loginRegister.setOnClickListener(this);
        return  rootView;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == loginRegister.getId()){
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        }

    }
}
