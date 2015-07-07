package com.lookapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.lookapp.R;
import com.lookapp.adapters.MenuAdapter;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.MenuItem;
import com.lookapp.bean.Spot;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;
import com.lookapp.swipelistview.SwipeDismissListViewTouchListener;
import com.lookapp.swipelistview.SwipeListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 07/07/2015.
 */
public class MenuActivity extends CustomActivity implements View.OnClickListener{

    Button addMenuItem;
    SwipeListView listView;
    MenuAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        addMenuItem = (Button)findViewById(R.id.add_menu_btn);
        addMenuItem.setOnClickListener(this);
        listView = (SwipeListView)findViewById(R.id.menu_list);

        adapter = new MenuAdapter(new ArrayList<MenuItem>(),getLayoutInflater());
        listView.setAdapter(adapter);

        listView.setupSwipeToDismiss(new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {

                for (int pos : reverseSortedPositions) {
                    deleteMenuItem(adapter.getItem(pos).getId());
                    adapter.getList().remove(adapter.getItem(pos));
                }

            }
        }, false);


        downloadMenu(app.getAdminSpotId());

    }

    private void deleteMenuItem(final long id) {

        LookAppTask<Void> task = new LookAppTask<Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try {
                    las.deleteMenuItem(id);
                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(exception == null){
                    MenuActivity.this.downloadMenu(app.getAdminSpotId());
                }else {
                    //TODO
                }
            }
        };
        task.execute();

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == addMenuItem.getId()){
            Intent intent = new Intent(this,AddMenuItemActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                downloadMenu(app.getAdminSpotId());
            }
        }
    }

    private void downloadMenu(final long spotId){
        LookAppTask<List<MenuItem>> task = new LookAppTask<List<MenuItem>>() {
            @Override
            protected List<MenuItem> doInBackground(Void... params) {

                LookAppService las = LookAppService.getInstance();
                try {
                    return las.getMenu(spotId);
                } catch (LookAppException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<MenuItem> menuItems) {
                adapter.setMenuItems(menuItems);
                adapter.notifyDataSetChanged();
            }
        };
        task.execute();
    }


}
