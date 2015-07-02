package com.lookapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lookapp.R;
import com.lookapp.bean.Spot;

/**
 * Created by giorgi-matiashvili on 7/2/2015.
 */
public class SpotDetailsActivity extends CustomActivity{

    private Spot spot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_details);
        int pos = (int) getIntent().getExtras().get("spotPosition");
        spot = app.getSpotList().get(pos);
        fillData();
    }

    private void fillData() {
        ((TextView)findViewById(R.id.spot_details_name)).setText(spot.getSpotName());
        ((TextView)findViewById(R.id.spot_details_description)).setText(spot.getDescription());
        ((TextView)findViewById(R.id.spot_details_address)).setText(spot.getSpotAddress());
        ((TextView)findViewById(R.id.spot_details_contact_info)).setText(spot.getContactInfo());
        ((TextView)findViewById(R.id.spot_details_contact_info)).setText(spot.getContactInfo());
        ((TextView)findViewById(R.id.spot_details_wifi_password)).setText(spot.getWifiPassword());
        if(!spot.isHasWifi()){
            findViewById(R.id.spot_details_wifi_panel).setVisibility(View.GONE);
        }
        if(spot.isHasNonSmokerArea()){
            ((ImageView)findViewById(R.id.spot_details_smoking_image)).setImageDrawable(getDrawable(R.drawable.wifi));
            // TODO
        }

//        if()
    }
}
