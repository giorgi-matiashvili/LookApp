package com.lookapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lookapp.R;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;

/**
 * Created by user on 06/07/2015.
 */
public class AnswerBookingActivity extends CustomActivity implements View.OnClickListener{
    private EditText answerBookingEt;
    private long spotId;
    private String number;
    private String spotName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_booking);
        answerBookingEt = (EditText)findViewById(R.id.answer_booking_et);
        findViewById(R.id.answer_booking_btn).setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        spotId = b.getLong("spotId");
        number = b.getString("number");
        spotName = b.getString("spotName");

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.answer_booking_btn){
            LookAppTask<Void> task = new LookAppTask<Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    LookAppService las = LookAppService.getInstance();
                    try {
                        las.answerBooking(spotId,spotName,answerBookingEt.getText().toString(),number);
                    } catch (LookAppException e) {
                        exception = e;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if(exception==null){
                        Intent returnIntent = new Intent();
                        setResult(RESULT_CANCELED, returnIntent);
                        finish();
                    }
                }
            };
            task.execute();
        }
    }
}
