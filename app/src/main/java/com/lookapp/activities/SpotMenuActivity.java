package com.lookapp.activities;

import android.os.Bundle;
import android.widget.ListView;

import com.lookapp.R;
import com.lookapp.adapters.MenuAdapter;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.MenuItem;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;
import com.lookapp.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi-matiashvili on 7/7/2015.
 */
public class SpotMenuActivity extends CustomActivity {

    private ListView listView;
    private MenuAdapter adapter;
    private long spotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_menu);

        spotId = getIntent().getExtras().getLong("spotId");

        listView = (ListView) findViewById(R.id.spot_menu_list);
        adapter = new MenuAdapter(new ArrayList<MenuItem>(), getLayoutInflater());
        listView.setAdapter(adapter);
        downloadMenu(spotId);
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
                if(menuItems != null){
                    UiUtils.showToast("MENU DOWNLOAD SUCCESS", SpotMenuActivity.this);
                    adapter.setMenuItems(menuItems);
                    adapter.notifyDataSetChanged();
                }else{
                    UiUtils.showToast("MENU DOWNLOAD FAILED", SpotMenuActivity.this);

                }
            }
        };
        task.execute();
    }
}
