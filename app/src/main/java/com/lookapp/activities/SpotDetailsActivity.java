package com.lookapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lookapp.R;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.Spot;
import com.lookapp.settings.Settings;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;

import java.util.Collections;

/**
 * Created by giorgi-matiashvili on 7/2/2015.
 */
public class SpotDetailsActivity extends CustomActivity implements View.OnClickListener{

    private Spot spot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_details);
        int pos = (int) getIntent().getExtras().get("spotPosition");
        spot = app.getSpotList().get(pos);
        fillData();
        downloadCover();
    }

    private void downloadCover() {
        LookAppTask<byte[]> task = new LookAppTask<byte[]>() {
            @Override
            protected byte[] doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
//                las.get

                return null;
            }
        };
        task.execute();
    }

    private void fillData() {
        TextView txt;
        ((TextView)findViewById(R.id.spot_details_name)).setText(spot.getSpotName());
        txt = ((TextView)findViewById(R.id.spot_details_rating));
        txt.setText(spot.getRating());
        txt.setOnClickListener(this);
        ((TextView)findViewById(R.id.spot_details_description)).setText(spot.getDescription());
        txt = ((TextView)findViewById(R.id.spot_details_address));
        txt.setText(spot.getSpotAddress());
        txt.setOnClickListener(this);
        ((TextView)findViewById(R.id.spot_details_contact_info)).setText(spot.getContactInfo());
        ((TextView)findViewById(R.id.spot_details_event_description)).setText(spot.getEventDescription());
        ((TextView)findViewById(R.id.spot_details_wifi_password)).setText(spot.getWifiPassword());
        if(!spot.isHasWifi()){
            findViewById(R.id.spot_details_wifi_panel).setVisibility(View.GONE);
        }
        if(spot.isHasNonSmokerArea()){
            ((ImageView)findViewById(R.id.spot_details_smoking_image)).setImageResource(R.drawable.check);
        }

        if(isInFavourites(spot)){
            ((ImageView)findViewById(R.id.spot_details_favorite_icon)).setImageResource(R.drawable.ic_rating_on);
        }
        findViewById(R.id.spot_details_favorite_icon).setOnClickListener(this);

        logger.d("favourites current spot " + spot.getSpotId());
        for (Spot s : app.getFavouritesList()){
            logger.d("favourites List spot " + s.getSpotId());
        }

    }

    private boolean isInFavourites(Spot spot) {
        for(Spot s : app.getFavouritesList()){
            if(s.getSpotId() == spot.getSpotId())
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.spot_details_rating){
            //TODO: open rating dialog
        }else if(id == R.id.spot_details_address){
            //TODO: open map
        }else if(id == R.id.spot_details_menu){
            //TODO: open menu activity
        }else if(id == R.id.spot_details_reserve_btn){
            //TODO: do reserve
        }else if(id == R.id.spot_details_favorite_icon){
            if(app.isLoggedIn()){
                addToFavorites(app.getSessionId(), spot.getSpotId(), !isInFavourites(spot));
            }else{
                addToSharedPreferences(!isInFavourites(spot));
            }

        }
    }

    private void addToSharedPreferences(boolean add) {
        if (add){
            app.getFavouritesList().add(spot);
            ((ImageView)findViewById(R.id.spot_details_favorite_icon)).setImageResource(R.drawable.ic_rating_on);
        }else{
            app.getFavouritesList().remove(spot);
            ((ImageView)findViewById(R.id.spot_details_favorite_icon)).setImageResource(R.drawable.ic_rating_off);
        }
        Settings.setFavouritesList();
    }

    private void addToFavorites(final String sessionId, final long spotId, final boolean add) {
        LookAppTask<Void> task = new LookAppTask<Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try {
                    if(add){
                        las.addFavourite(sessionId, spotId);
                    }else{
                        las.deleteFavourite(sessionId, spotId);
                    }
                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(exception != null){
                    String text = SpotDetailsActivity.this.getResources().getString(R.string.favourites_delete_error);
                    if(add){
                        text = SpotDetailsActivity.this.getResources().getString(R.string.favourite_add_error);
                    }
                    Toast.makeText(SpotDetailsActivity.this, text,
                            Toast.LENGTH_LONG).show();
                }else {
                    if(add){
                        ((ImageView)findViewById(R.id.spot_details_favorite_icon)).setImageResource(R.drawable.ic_rating_on);
                        app.getFavouritesList().add(spot);
                    }else{
                        ((ImageView)findViewById(R.id.spot_details_favorite_icon)).setImageResource(R.drawable.ic_rating_off);
                        app.getFavouritesList().remove(spot);
                    }
                }
            }
        };


        task.execute();
    }
}
