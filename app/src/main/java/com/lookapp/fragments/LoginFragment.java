package com.lookapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lookapp.R;
import com.lookapp.activities.AuthorizedActivity;
import com.lookapp.activities.RegisterActivity;
import com.lookapp.api.RequestInvoker;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.LoginResponse;
import com.lookapp.listeners.LoginDataDownloadListener;
import com.lookapp.settings.Settings;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;

/**
 * Created by user on 02/07/2015.
 */
public class LoginFragment extends CustomFragment implements View.OnClickListener , LoginDataDownloadListener {
    private View rootView;
    private TextView loginRegister, errortext;
    private EditText userName;
    private EditText password;
    private ProgressDialog progress;
    private View saveUsername;
    private CheckBox saveUsernameCheckBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        loginRegister = (TextView) rootView.findViewById(R.id.login_fragment_register);
        loginRegister.setOnClickListener(this);

        userName = (EditText) rootView.findViewById(R.id.et_login_username);
        password = (EditText) rootView.findViewById(R.id.et_login_password);
        errortext = (TextView) rootView.findViewById(R.id.tv_error_message);
        saveUsername = rootView.findViewById(R.id.save_username_area);
        saveUsername.setOnClickListener(this);
        saveUsernameCheckBox = (CheckBox) rootView.findViewById(R.id.chb_save_name);
        saveUsernameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    Settings.saveUserName("");
                }
            }
        });
        rootView.findViewById(R.id.login_btn).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == loginRegister.getId()) {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        }else if (view.getId() == R.id.login_btn) {
            if(userName.getText().length() != 0 &&  password.getText().length() != 0){
                errortext.setText("");
                if(saveUsernameCheckBox.isChecked()){
                    Settings.saveUserName(userName.getText().toString());
                }
                login(userName.getText().toString(), password.getText().toString());
            }else{
                errortext.setText(getResources().getString(R.string.no_login_data_warning));
            }
        }else if(view.getId() == R.id.save_username_area){

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
                    if(exception.getLookAppError() != null){
                        errortext.setText(exception.getLookAppError().getMessage());
                    }
                } else {
                    progress = ProgressDialog.show(getActivity(), "", getString(R.string.loading_data), true);
                    RequestInvoker.getInstance().onLogin(LoginFragment.this);
                    app.setSessionId(loginResponse.getSessionId());
                    app.setAdminSpotId(loginResponse.getAdminSpotId());
                }
            }
        };
        loginTask.execute();

    }

    @Override
    public void onLoginDataDownloaded(boolean wasSuccesful) {

        if(wasSuccesful){
            app.setIsLoggedIn(true);
            Intent intent = new Intent(getActivity(), AuthorizedActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(), getResources().getString(R.string.login_error),
                    Toast.LENGTH_LONG).show();
        }

        if(progress != null){
            progress.dismiss();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        userName.setText(Settings.getUserName());
    }
}
