package com.lookapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lookapp.R;
import com.lookapp.Tasks.AvatarLookAppTask;
import com.lookapp.activities.SpotDetailsActivity;
import com.lookapp.adapters.SpotListAdapter;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.Spot;
import com.lookapp.listeners.AvatarDownloadListener;
import com.lookapp.settings.Settings;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;
import com.lookapp.swipelistview.SwipeDismissListViewTouchListener;
import com.lookapp.swipelistview.SwipeListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 02/07/2015.
 */
public class FavouritesFragment extends CustomFragment implements  ListView.OnItemClickListener {

    private static final int SPOT_DETAILS_ACTIVITY_CODE = 1;

    private SwipeListView spotList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SpotListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        spotList = (SwipeListView) view.findViewById(R.id.spot_list);
        spotList.setOnItemClickListener(this);
        adapter = new SpotListAdapter(app.getFavouritesList(), activity.getLayoutInflater());
        spotList.setAdapter(adapter);


        spotList.setupSwipeToDismiss(new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {

                for (int pos : reverseSortedPositions) {
                    try {
                        Spot s = app.getFavouritesList().remove(pos);
                        deleteFavourite(app.getSessionId(), s.getSpotId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



            }
        }, false);
        createSwipeRefresh(view);
        downloadFavouritesList(true);
        return view;
    }


    private void deleteFavourite(final String sessionId, final long spotId){
        LookAppTask<Void> deleteFavouriteTask = new LookAppTask<Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try{
                    las.deleteFavourite(sessionId,spotId);
                }catch (LookAppException e){
                    exception = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                String spotName = null;
                for(Spot s : app.getSpotList()){
                    if(s.getSpotId() == spotId){
                        spotName = s.getSpotName();
                    }
                }
                if(exception == null){
                    Toast.makeText(getActivity(), spotName+ " "+getResources().getString(R.string.favourites_delete_success),
                            Toast.LENGTH_LONG).show();
                    downloadFavouritesList(false);
                }else {
                    Toast.makeText(getActivity(), spotName+ " "+getResources().getString(R.string.favourites_delete_error),
                            Toast.LENGTH_LONG).show();
                }

            }
        };
        if(app.isLoggedIn()){
            deleteFavouriteTask.execute();
        }else {
            Settings.setFavouritesList();
            downloadFavouritesList(false);
        }

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


        if(app.isLoggedIn()){
            task.execute();
        }else {
            app.setFavouritesList(Settings.getFavouritesList());
            adapter.setSpotList(Settings.getFavouritesList());
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), SpotDetailsActivity.class);
        intent.putExtra("spotId", adapter.getItemId(position));
        startActivityForResult(intent, SPOT_DETAILS_ACTIVITY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SPOT_DETAILS_ACTIVITY_CODE){
            logger.d("SpotDetails finished, refreshing favorite list");
            downloadFavouritesList(true);
        }
    }

}
