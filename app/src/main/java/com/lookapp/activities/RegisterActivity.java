package com.lookapp.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lookapp.R;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.SmsCode;
import com.lookapp.settings.Settings;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;
import com.lookapp.utils.UiUtils;

/**
 * Created by user on 02/07/2015.
 */
public class RegisterActivity extends CustomActivity implements View.OnClickListener {

    EditText userName, fullName, password, repeatPassword, numberEt, smsCodeEt;
    private SmsCode smsCode;
    private Button smsButton, registerButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = (EditText) findViewById(R.id.register_username_et);
        password = (EditText) findViewById(R.id.register_password_et);
        numberEt = (EditText) findViewById(R.id.register_phone_number_et);
        smsCodeEt = (EditText) findViewById(R.id.register_sms_code_et);
        fullName = (EditText) findViewById(R.id.register_full_name_et);
        repeatPassword = (EditText) findViewById(R.id.register_password_repeat_et);
        smsButton = (Button) findViewById(R.id.register_sms_code_get_btn);
        registerButton = (Button) findViewById(R.id.register_btn);

        smsButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.register_sms_code_get_btn) {
            if(numberEt.getText().length() > 0){
                getSmsCode(numberEt.getText().toString());
            }else{
                animateView(numberEt);
            }
        }else if (view.getId() == R.id.register_btn) {
            if(dataIsFilled()){
                register(fullName.getText().toString(), userName.getText().toString(), password.getText().toString(), numberEt.getText().toString());
            }

        }

    }

    private boolean dataIsFilled() {
        boolean result = true;
        if (userName.getText().length() == 0) {
            animateView(userName);
            result = false;
        }

        if (fullName.getText().length() == 0) {
            animateView(fullName);
            result = false;
        }

        if (numberEt.getText().length() == 0) {
            animateView(numberEt);
            result = false;
        }

        if (password.getText().length() == 0 || !password.getText().toString().equals(repeatPassword.getText().toString())) {
            animateView(password);
            animateView(repeatPassword);
            result = false;
        }

        if (smsCode == null || smsCodeEt.getText().length() == 0 || !smsCodeEt.getText().toString().equals(smsCode.getSmsCode())) {
                animateView(smsCodeEt);
                result = false;
        }

        return result;

    }

    private void animateView(View v) {
//        v.setBackgroundResource(R.drawable.money_transfer_edittext_warning_shape);
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wiggle_anim));
    }

    private void register(final String fullName, final String userName, final String password, final String number) {

        LookAppTask<Void> registerTask = new LookAppTask<Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                LookAppService las = LookAppService.getInstance();
                try {
                    las.register(fullName, userName, password, Settings.NUMBER_PREFIX + number);
                } catch (LookAppException e) {
                    exception = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (exception != null) {
                    Toast.makeText(getApplicationContext(), exception.getLookAppError().getMessage(),
                            Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.register_ok),
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        };
        registerTask.execute();

    }

    private void getSmsCode(String text) {

        final String number = Settings.NUMBER_PREFIX + text;

        LookAppTask<SmsCode> smsCodeTask = new LookAppTask<SmsCode>() {
            @Override
            protected SmsCode doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try {
                    return las.getSmsCode(number);
                } catch (LookAppException e) {
                    exception = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(SmsCode smsCode) {
                String message = null;
                if (smsCode != null) {
                    RegisterActivity.this.smsCode = smsCode;
                    message = getResources().getString(R.string.sending_sms_code_success_text);
                }else{
                    message = getResources().getString(R.string.sending_sms_code_failed_text);
                }
                UiUtils.showToast(message, RegisterActivity.this);
            }
        };

        smsCodeTask.execute();

    }


}
