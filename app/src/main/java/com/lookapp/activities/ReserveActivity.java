package com.lookapp.activities;

import android.os.Bundle;
import android.view.View;
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
    }

    @Override
    public void onClick(View view) {
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
}
