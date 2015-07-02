package com.lookapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lookapp.R;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.SmsCode;
import com.lookapp.settings.Settings;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;

/**
 * Created by user on 02/07/2015.
 */
public class RegisterActivity extends CustomActivity implements View.OnClickListener{

    EditText userName,password, numberEt,smsCodeEt;
    private SmsCode smsCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = (EditText)findViewById(R.id.register_username);
        password = (EditText)findViewById(R.id.register_password);
        numberEt = (EditText)findViewById(R.id.register_number);
        smsCodeEt = (EditText)findViewById(R.id.register_sms_code_enter);

        findViewById(R.id.register_sms_code_get_btn).setOnClickListener(this);
        findViewById(R.id.register_btn).setOnClickListener(this);


    }




    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.register_sms_code_get_btn){
            getSmsCode(numberEt.getText().toString());
        }

        if(view.getId() == R.id.register_btn){

        }

    }

    private void getSmsCode(String  text) {

        final String number = Settings.NUMBER_PREFIX + text;

        LookAppTask<SmsCode> smsCodeTask = new LookAppTask<SmsCode>() {
            @Override
            protected SmsCode doInBackground(Void... params) {

                SmsCode smsCode = null;
                LookAppService las = LookAppService.getInstance();

                try{
                    return las.getSmsCode(number);
                }catch (LookAppException e){
                    exception = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(SmsCode smsCode) {
                if(smsCode != null){
                    RegisterActivity.this.smsCode = smsCode;
                }
            }
        };

        smsCodeTask.execute();

    }


}
