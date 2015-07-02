package com.lookapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lookapp.R;
import com.lookapp.activities.AuthorizedActivity;
import com.lookapp.activities.RegisterActivity;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.LoginResponse;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;

/**
 * Created by user on 02/07/2015.
 */
public class LoginFragment extends CustomFragment implements View.OnClickListener {
    private View rootView;
    private TextView loginRegister;
    private EditText userName;
    private EditText password;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        loginRegister = (TextView) rootView.findViewById(R.id.login_register);
        loginRegister.setOnClickListener(this);

        userName = (EditText) rootView.findViewById(R.id.login_username);
        password = (EditText) rootView.findViewById(R.id.login_password);

        rootView.findViewById(R.id.login_btn).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == loginRegister.getId()) {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        }

        if (view.getId() == R.id.login_btn) {
            login(userName.getText().toString(), password.getText().toString());

        }

    }

    private void login(final String userName, final String password) {

        LookAppTask<LoginResponse> loginTask = new LookAppTask<LoginResponse>() {
            @Override
            protected LoginResponse doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try {
                    return las.login(userName, password);
                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(LoginResponse loginResponse) {
                if (exception != null) {
                    Toast.makeText(app.getApplicationContext(), exception.getLookAppError().getMessage(),
                            Toast.LENGTH_LONG).show();
                } else {
                    app.setSessionId(loginResponse.getSessionId());
                    Intent intent = new Intent(getActivity(), AuthorizedActivity.class);
                    startActivity(intent);
                }
            }
        };
        loginTask.execute();

    }
}
