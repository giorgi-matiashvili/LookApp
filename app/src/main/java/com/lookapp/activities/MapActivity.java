package com.lookapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lookapp.App;
import com.lookapp.R;
import com.lookapp.bean.Spot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi-matiashvili on 7/8/2015.
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final  int INITIAL_ZOOM = 14;
    private List<Spot> spotObjects;
    private static int selected;

    private GoogleMap map;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        selected = getIntent().getExtras().getInt("selected");
//        selected = 0;
//        Spot sp = new Spot();
//        sp.setSpotAddress("ვაჟა ფშაველა");
//        sp.setType("კაფე, აბრი, რესტორანი");
//        sp.setLatitude(41.7273070);
//        sp.setLongitude(44.7634988);
//        spotObjects = new ArrayList<>();
//        spotObjects.add(sp);
        spotObjects = App.getInstance().getSpotList();
        Log.d("map", "ON CREATE MOTHERFUCKER MAP");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void addMarkers() {
        map.setMyLocationEnabled(true);

        Marker selectedObject = null;
        for (int i = 0; i < spotObjects.size(); i++) {
            Spot serviceObject = spotObjects.get(i);
            Marker m = map.addMarker(new MarkerOptions().
                    position(new LatLng(serviceObject.getLatitude(), serviceObject.getLongitude())).
                    title(serviceObject.getSpotAddress()).
                    snippet(serviceObject.getType()).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker)));
            if (i == selected) {
                selectedObject = m;
            }
        }
        if (selectedObject != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedObject.getPosition(), INITIAL_ZOOM));
            selectedObject.showInfoWindow();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        MapsInitializer.initialize(App.getInstance());
        addMarkers();
    }


}
