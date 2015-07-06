package com.lookapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lookapp.R;
import com.lookapp.api.request.ReserveRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 06/07/2015.
 */
public class BookingListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ReserveRequest> bookingInfos;

    public BookingListAdapter(ArrayList<ReserveRequest> bookingInfos, LayoutInflater layoutInflater){
        this.bookingInfos = bookingInfos;
        this.inflater = layoutInflater;

    }

    @Override
    public int getCount() {
        return bookingInfos.size();
    }

    @Override
    public ReserveRequest getItem(int i) {
        return bookingInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.booking_list_item, parent, false);
            holder = new ViewHolder();
            holder.userName = (TextView) convertView.findViewById(R.id.user_name);
            holder.number = (TextView) convertView.findViewById(R.id.user_number);
            holder.sits = (TextView) convertView.findViewById(R.id.user_sits);
            holder.time = (TextView) convertView.findViewById(R.id.user_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.userName.setText(bookingInfos.get(position).getSessionId());
        holder.number.setText(bookingInfos.get(position).getNumber());
        holder.sits.setText(bookingInfos.get(position).getSits());
        holder.time.setText(bookingInfos.get(position).getTime());



        return convertView;
    }

    public void setBookingInfos(List<ReserveRequest> bookingInfos) {
        this.bookingInfos = bookingInfos;
    }



    private static class ViewHolder {
        TextView userName;
        TextView number;
        TextView sits;
        TextView time;
    }




}
