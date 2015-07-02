package com.lookapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lookapp.R;
import com.lookapp.Tasks.AvatarLookAppTask;
import com.lookapp.adapters.SpotListAdapter;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.Spot;
import com.lookapp.listeners.AvatarDownloadListener;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 02/07/2015.
 */
public class FavouritesFragment extends CustomFragment implements  ListView.OnItemClickListener {


    private ListView spotList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SpotListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spot_list, container, false);
        spotList = (ListView) view.findViewById(R.id.spot_list);
        spotList.setOnItemClickListener(this);
        adapter = new SpotListAdapter(app.getFavouritesList(), activity.getLayoutInflater());
        spotList.setAdapter(adapter);
        createSwipeRefresh(view);
        downloadFavouritesList(true);
        return view;
    }

    private void createSwipeRefresh(View layout) {
        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.spot_list_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                logger.i("swipe refresh");
                downloadFavouritesList(true);
            }
        });
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    private void downloadFavouritesList(final boolean showRefresh) {
        LookAppTask<List<Spot>> task = new LookAppTask<List<Spot>>() {

            @Override
            protected void onPreExecute() {
                swipeRefreshLayout.setRefreshing(showRefresh);
            }

            @Override
            protected List<Spot> doInBackground(Void... params) {

                LookAppService las = LookAppService.getInstance();
                try {
                    List<Long> favouriteIds = las.getFavouriteSpotIds(app.getSessionId());
                    List<Spot> spots = new ArrayList<Spot>();

                    for(Spot s : app.getSpotList()){
                        for(long id : favouriteIds){
                            if(s.getSpotId() == id){
                                spots.add(s);
                            }
                        }
                    }

                    return spots;

                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Spot> spots) {
                swipeRefreshLayout.setRefreshing(false);
                if(spots!=null){
                    app.setFavouritesList(spots);
                    adapter.setSpotList(spots);
                    adapter.notifyDataSetChanged();
                }
            }
        };

        task.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
