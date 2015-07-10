package com.lookapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lookapp.R;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.RatingResponse;
import com.lookapp.bean.Spot;
import com.lookapp.settings.Settings;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;
import com.lookapp.utils.UiUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by giorgi-matiashvili on 7/2/2015.
 */
public class SpotDetailsActivity extends CustomActivity implements View.OnClickListener{

    private Spot spot;
    private ImageView cover;
    private Dialog ratingDialog;
    private RatingBar ratingBar;
    private TextView ratingTv;
    private int selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_details);
        long id = (long) getIntent().getExtras().get("spotId");
        spot = getSpot(id);
        cover = (ImageView) findViewById(R.id.spot_details_cover);

        fillData();
        downloadCover();
    }

    private Spot getSpot(long id) {
        for(int i = 0; i < app.getSpotList().size(); i++){
            Spot s = app.getSpotList().get(i);
            if(s.getSpotId() == id){
                selected = i;
                return s;
            }
        }
        return null;
    }

    private void downloadCover() {
        LookAppTask<Bitmap> task = new LookAppTask<Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try {
                    byte[] bytes = Base64.decode(las.getCoverImage(spot.getSpotId()), Base64.DEFAULT);
                    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bytes) {
                if(bytes != null){
                    cover.setImageBitmap(bytes);
                }
            }
        };
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void fillData() {
        TextView txt;
        ((TextView)findViewById(R.id.spot_details_name)).setText(spot.getSpotName());
        ratingTv  = ((TextView)findViewById(R.id.spot_details_rating));
        ratingTv.setText(spot.getRating());
        ratingTv.setOnClickListener(this);
        ((TextView)findViewById(R.id.spot_details_description)).setText(spot.getDescription());
        txt = ((TextView)findViewById(R.id.spot_details_address));
        txt.setText(spot.getSpotAddress());
        txt.setOnClickListener(this);
        ((TextView)findViewById(R.id.spot_details_address_label)).setOnClickListener(this);
        ((TextView)findViewById(R.id.spot_details_contact_info)).setText(spot.getContactInfo());
        ((TextView)findViewById(R.id.spot_details_event_description)).setText(spot.getEventDescription());
        ((TextView)findViewById(R.id.spot_details_wifi_password)).setText(spot.getWifiPassword());
        if(!spot.isHasWifi()){
            findViewById(R.id.spot_details_wifi_panel).setVisibility(View.GONE);
        }
        if(spot.isHasNonSmokerArea()){
            ((TextView)findViewById(R.id.spot_details_smoking_area_tv)).setText(getResources().getString(R.string.spot_details_has_smoking_zone_text));
        }

        if(isInFavourites(spot)){
            ((ImageView)findViewById(R.id.spot_details_favorite_icon)).setImageResource(R.drawable.ic_rating_on);
        }
        findViewById(R.id.spot_details_favorite_icon).setOnClickListener(this);

//        logger.d("favourites current spot " + spot.getSpotId());
//        for (Spot s : app.getFavouritesList()){
//            logger.d("favourites List spot " + s.getSpotId());
//        }

        ((TextView) findViewById(R.id.spot_details_working_hours)).setText(spot.getWorkingHours());

        findViewById(R.id.spot_details_reserve_btn).setOnClickListener(this);
        findViewById(R.id.spot_details_menu).setOnClickListener(this);

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
            if(app.isLoggedIn()){
                showRatingDialog();
            }else{
                UiUtils.showAlertDialog(getResources().getString(R.string.spot_rating_alert_text), this);
            }
        }else if(id == R.id.spot_details_address || id == R.id.spot_details_address_label){
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("selected", selected);
            startActivity(intent);
        }else if(id == R.id.spot_details_menu){
            Intent intent = new Intent(this, SpotMenuActivity.class);
            intent.putExtra("spotId", spot.getSpotId());
            startActivity(intent);
        }else if(id == R.id.spot_details_reserve_btn){
            if(app.isLoggedIn() && spot.isCanReservePlace()){
                Intent intent = new Intent(this, ReserveActivity.class);
                intent.putExtra("spotId", spot.getSpotId());
                startActivity(intent);
            } if(!spot.isCanReservePlace()) {
                UiUtils.showAlertDialog(getResources().getString(R.string.no_reserve_available_text), this);
            }else if (!app.isLoggedIn()){
                UiUtils.showAlertDialog(getResources().getString(R.string.spot_reserve_alert_text), this);
            }
        }else if(id == R.id.spot_details_favorite_icon){
            if(app.isLoggedIn()){
                addToFavorites(app.getSessionId(), spot.getSpotId(), !isInFavourites(spot));
            }else{
                addToSharedPreferences(!isInFavourites(spot));
            }

        }
    }

    private void showRatingDialog() {
        ratingDialog = new Dialog(this);
        ratingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ratingDialog.setCancelable(false);
        ratingDialog.setContentView(R.layout.rating_dialog_layout);
        ratingBar = (RatingBar) ratingDialog.findViewById(R.id.spot_details_rating_bar);
        ratingBar.setRating(Float.parseFloat(spot.getRating()));
        final TextView currRating = (TextView) ratingDialog.findViewById(R.id.rating_bar_current_rating);
        currRating.setText(spot.getRating());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float value, boolean b) {
                currRating.setText("" + value);
            }
        });

        ratingDialog.findViewById(R.id.rating_dialog_btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingDialog.dismiss();
            }
        });

        ratingDialog.findViewById(R.id.rating_dialog_btn_rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateSpot(ratingBar.getRating());
                ratingDialog.dismiss();
            }

            private void rateSpot(final float rating) {
                LookAppTask<RatingResponse> task = new LookAppTask<RatingResponse>() {
                    @Override
                    protected RatingResponse doInBackground(Void... params) {
                        LookAppService las = LookAppService.getInstance();
                        try {
                            return las.addRating(app.getSessionId(), spot.getSpotId(), rating);
                        } catch (LookAppException e) {
                            exception = e;
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(RatingResponse ratingResponse) {
                        if(ratingResponse != null){
                            ratingTv.setText(""+ratingResponse.getNewRating());
                            spot.setRating(""+ratingResponse.getNewRating());
                        }
                    }
                };

                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        ratingDialog.show();
    }

    private void addToSharedPreferences(boolean add) {
        if (add){
            app.getFavouritesList().add(spot);
            ((ImageView)findViewById(R.id.spot_details_favorite_icon)).setImageResource(R.drawable.ic_rating_on);
        }else{
            removeSpot(app.getFavouritesList(), spot);
            ((ImageView)findViewById(R.id.spot_details_favorite_icon)).setImageResource(R.drawable.ic_rating_off);
        }
        Settings.setFavouritesList();
    }

    private void removeSpot(List<Spot> list, Spot spot) {
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getSpotId() == spot.getSpotId()){
                list.remove(i);
                break;
            }
        }
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
                        removeSpot(app.getFavouritesList(), spot);
                    }
                }
            }
        };


        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
