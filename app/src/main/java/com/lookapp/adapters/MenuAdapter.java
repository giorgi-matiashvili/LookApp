package com.lookapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lookapp.R;
import com.lookapp.bean.MenuItem;

import java.util.List;

/**
 * Created by user on 07/07/2015.
 */
public class MenuAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<MenuItem> menuItems;

    public MenuAdapter(List<MenuItem> menuItems, LayoutInflater inflater) {

        this.inflater = inflater;
        this.menuItems = menuItems;

    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public MenuItem getItem(int i) {
        return menuItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.menu_list_item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.menu_item_name);
            holder.description = (TextView) convertView.findViewById(R.id.menu_item_description);
            holder.price = (TextView) convertView.findViewById(R.id.menu_item_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(menuItems.get(position).getNameToShow());
        holder.description.setText(menuItems.get(position).getDescriptionToShow());
        holder.price.setText(menuItems.get(position).getPrice() + "");

        if(position == menuItems.size()-1){
            convertView.findViewById(R.id.divider).setVisibility(View.GONE);
        }else {
            convertView.findViewById(R.id.divider).setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<MenuItem> getList() {
        return menuItems;
    }

    private static class ViewHolder {
        TextView name;
        TextView description;
        TextView price;
    }

}
