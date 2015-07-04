package com.lookapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lookapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giorgi on 6/21/2015.
 */
public class UnauthorizedDrawerAdapter extends BaseAdapter {

    private List<DrawerObject> drawerObjectList;
    private LayoutInflater inflater;

    public UnauthorizedDrawerAdapter(LayoutInflater inflater){
        this.inflater  = inflater;
        drawerObjectList = new ArrayList<DrawerObject>();
        drawerObjectList.add(new DrawerObject(inflater.getContext().getResources().getString(R.string.login), R.drawable.ic_drawer_home));
        drawerObjectList.add(new DrawerObject(inflater.getContext().getResources().getString(R.string.spot_list), R.drawable.ic_drawer_home));
        drawerObjectList.add(new DrawerObject(inflater.getContext().getResources().getString(R.string.choose_language), R.drawable.ic_drawer_home));
    }

    @Override
    public int getCount() {
        return drawerObjectList.size();
    }

    @Override
    public DrawerObject getItem(int position) {
        return drawerObjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.drawer_item, parent, false);
            vh.text = (TextView)convertView.findViewById(R.id.drawer_item_text);
            vh.img = (ImageView)convertView.findViewById(R.id.drawer_item_img);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        vh.text.setText(getItem(position).text);
        vh.img.setImageResource(getItem(position).imgId);


        return convertView;
    }

    private class DrawerObject{

        public DrawerObject(String text,int imgId){

            this.text = text;
            this.imgId = imgId;

        }

        String text;
        int imgId;
    }

    private class ViewHolder{
        ImageView img;
        TextView text;
    }

}
