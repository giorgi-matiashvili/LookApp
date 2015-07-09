package com.lookapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.lookapp.R;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;
import com.lookapp.utils.UiUtils;

/**
 * Created by giorgi-matiashvili on 7/6/2015.
 */
public class ReserveActivity extends CustomActivity implements View.OnClickListener{

    private static final int MAX_COMMENT_LETTERS = 100;

    private EditText number, time, comment;
    private Button reserveButton;
    private long spotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        spotId = getIntent().getExtras().getLong("spotId");
        number = (EditText) findViewById(R.id.spot_reserve_number_of_places_et);
        time = (EditText) findViewById(R.id.spot_reserve_visit_time_et);
        comment = (EditText) findViewById(R.id.spot_reserve_comment_et);
        reserveButton = (Button) findViewById(R.id.spot_reserve_reserve_btn);
        reserveButton.setOnClickListener(this);

        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = comment.getText().toString();
                if (text.length() > MAX_COMMENT_LETTERS) {
                    comment.setText(text.substring(0, MAX_COMMENT_LETTERS));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(isInvalidData())
            return;
        LookAppTask<Void> task = new LookAppTask<Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try {
                    las.reserve(app.getSessionId(), time.getText().toString(), number.getText().toString(), comment.getText().toString(), spotId);
                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(exception == null){
                    UiUtils.showToast(getResources().getString(R.string.reserve_success_message), ReserveActivity.this);
                    finish();
                }else{
                    UiUtils.showToast(getResources().getString(R.string.reserve_unsuccess_message), ReserveActivity.this);
                }
            }
        };

        task.execute();
    }

    private boolean isInvalidData() {
        boolean result = false;
        if(number.getText().length() == 0){
            animateView(number);
            result = true;
        }

        if(time.getText().length() == 0) {
            animateView(time);
            result = true;
        }
        return result;
    }

    private void animateView(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wiggle_anim));
    }
}
