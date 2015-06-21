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

    public SpotListAdapter(List<Spot> spotList, LayoutInflater inflater){
        this.spotList = spotList;
        this.inflater = inflater;
    }

    public void setSpotList(List<Spot> spots){
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spot_list_item, parent, false);
            holder = new SpotHolder();
            holder.name = (TextView) convertView.findViewById(R.id.spot_name);
            holder.img = (ImageView) convertView.findViewById(R.id.spot_avatar);
            convertView.setTag(holder);
        } else {
            holder = (SpotHolder) convertView.getTag();
        }
        Spot item = getItem(position);
        holder.name.setText(item.getSpotName());

        if(item.getAvatar() != null) {
            holder.img.setImageBitmap(BitmapFactory.decodeByteArray(item.getAvatar(),0,item.getAvatar().length));
        }else {
            holder.img.setImageResource(R.drawable.ic_drawer_home);
        }

        return convertView;
    }

    private static class SpotHolder{
        TextView name;
        ImageView img;
    }
}
