package com.lookapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lookapp.R;

/**
 * Created by giorgi-matiashvili on 7/6/2015.
 */
public class ReserveActivity extends CustomActivity implements View.OnClickListener{

    private EditText number, time, comment;
    private Button reserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        number = (EditText) findViewById(R.id.spot_reserve_number_of_places_et);
        time = (EditText) findViewById(R.id.spot_reserve_visit_time_et);
        comment = (EditText) findViewById(R.id.spot_reserve_comment_et);
        reserveButton = (Button) findViewById(R.id.spot_reserve_reserve_btn);
        reserveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
