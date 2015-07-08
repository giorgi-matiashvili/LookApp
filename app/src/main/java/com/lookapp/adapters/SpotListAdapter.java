package com.lookapp.adapters;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lookapp.R;
import com.lookapp.bean.Spot;

import java.util.List;

/**
 * Created by Giorgi on 6/21/2015.
 */
public class SpotListAdapter extends BaseAdapter {

    private List<Spot> spotList;
    private LayoutInflater inflater;
    private String mainQuery = "";
    private String sitsQuery = "";
    private boolean hasWifi, hasNonSmokingArea, canReservePlace;

    public SpotListAdapter(List<Spot> spotList, LayoutInflater inflater) {
        this.spotList = spotList;
        this.inflater = inflater;
    }

    public void setSpotList(List<Spot> spots) {
        this.spotList = spots;
    }

    @Override
    public int getCount() {
        return spotList.size();
    }

    @Override
    public Spot getItem(int position) {
        return spotList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return spotList.get(position).getSpotId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpotHolder holder;

        if(canHandleFilter(position)){
            convertView = inflater.inflate(R.layout.spot_list_item, parent, false);
            holder = new SpotHolder();
            holder.name = (TextView) convertView.findViewById(R.id.spot_name);
            holder.type = (TextView) convertView.findViewById(R.id.spot_item_short_description);
            holder.rating = (TextView) convertView.findViewById(R.id.spot_item_rating);
            holder.places = (TextView) convertView.findViewById(R.id.spot_item_available_places);
            holder.workingHours = (TextView) convertView.findViewById(R.id.spot_item_working_hours);
            holder.avatar = (ImageView) convertView.findViewById(R.id.spot_avatar);
            holder.wifi = (ImageView) convertView.findViewById(R.id.spot_item_wifi_icon);

            Spot item = getItem(position);
            holder.name.setText(item.getSpotName());
            holder.type.setText(item.getType());
            holder.rating.setText(item.getRating());
            holder.workingHours.setText(item.getWorkingHours());
            holder.places.setText(item.getFreeSits());

            if (item.getAvatar() != null) {
                holder.avatar.setImageBitmap(BitmapFactory.decodeByteArray(item.getAvatar(), 0, item.getAvatar().length));
            } else {
                holder.avatar.setImageResource(R.drawable.ic_drawer_home);
            }

            if (item.isHasWifi()) {
                holder.wifi.setVisibility(View.VISIBLE);
            } else {
                holder.wifi.setVisibility(View.GONE);
            }

        }else {
            convertView = inflater.inflate(R.layout.null_layout, parent, false);
        }

        return convertView;
    }

    private boolean canHandleFilter(int position) {

        Spot spot = getItem(position);
        if (hasWifi && !spot.isHasWifi()) {

            return false;

        }

        if (hasNonSmokingArea && !spot.isHasNonSmokerArea()) {

            return false;

        }

        if (canReservePlace && !spot.isCanReservePlace()) {
            return false;
        }

        if (!spot.getSpotName().contains(mainQuery) && !spot.getType().contains(mainQuery)) {
            return false;
        }

        if (sitsQuery.equals("")) {
            return true;
        }

        if (spot.getFreeSits().equals("-")) {
            return false;
        }
        int sits = Integer.parseInt(sitsQuery);
        if (sits > Integer.parseInt(spot.getFreeSits())){
            return false;
        }

            return true;

    }

    private static class SpotHolder {
        TextView name, type, rating, places, workingHours;
        ImageView avatar, wifi;
    }


    public String getMainQuery() {
        return mainQuery;
    }

    public void setMainQuery(String mainQuery) {
        this.mainQuery = mainQuery;
    }

    public String getSitsQuery() {
        return sitsQuery;
    }

    public void setSitsQuery(String sitsQuery) {
        this.sitsQuery = sitsQuery;
    }

    public boolean isHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public boolean isHasNonSmokingArea() {
        return hasNonSmokingArea;
    }

    public void setHasNonSmokingArea(boolean hasNonSmokingArea) {
        this.hasNonSmokingArea = hasNonSmokingArea;
    }

    public boolean isCanReservePlace() {
        return canReservePlace;
    }

    public void setCanReservePlace(boolean canReservePlace) {
        this.canReservePlace = canReservePlace;
    }
}
