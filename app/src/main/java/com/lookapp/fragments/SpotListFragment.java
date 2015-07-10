package com.lookapp.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.lookapp.R;
import com.lookapp.Tasks.AvatarLookAppTask;
import com.lookapp.activities.SpotDetailsActivity;
import com.lookapp.adapters.SpotListAdapter;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.Spot;
import com.lookapp.listeners.AvatarDownloadListener;
import com.lookapp.listeners.SearchClickListener;
import com.lookapp.support.LookAppTask;
import com.lookapp.support.LookAppService;
import com.lookapp.swipelistview.SwipeListView;

import java.util.List;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class SpotListFragment extends CustomFragment implements  ListView.OnItemClickListener,AvatarDownloadListener,SearchClickListener{


    private static final int SPOT_DETAILS_ACTIVITY_CODE = 1;

    private SwipeListView spotList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SpotListAdapter adapter;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_spot_list, container, false);
        spotList = (SwipeListView) rootView.findViewById(R.id.spot_list);
        spotList.setOnItemClickListener(this);
        adapter = new SpotListAdapter(app.getSpotList(), activity.getLayoutInflater());
        spotList.setAdapter(adapter);
        createSwipeRefresh(rootView);

        initFilter();

        return rootView;
    }

    private void initFilter() {

        ((EditText)rootView.findViewById(R.id.search_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.setMainQuery(charSequence.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((EditText)rootView.findViewById(R.id.free_sits_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.setSitsQuery(charSequence.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((CheckBox)rootView.findViewById(R.id.has_wifi_check_box)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                adapter.setHasWifi(b);
                adapter.notifyDataSetChanged();
            }
        });
        ((CheckBox)rootView.findViewById(R.id.has_non_smoking_area_check_box)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                adapter.setHasNonSmokingArea(b);
                adapter.notifyDataSetChanged();
            }
        });
        ((CheckBox)rootView.findViewById(R.id.can_reserve_place_checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                adapter.setCanReservePlace(b);
                adapter.notifyDataSetChanged();
            }
        });




    }

    private void createSwipeRefresh(View layout) {
        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.spot_list_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                logger.i("swipe refresh");
                downloadSpotList(true);
            }
        });
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    private void downloadSpotList(final boolean showRefresh) {
        LookAppTask<List<Spot>> task = new LookAppTask<List<Spot>>() {

            @Override
            protected void onPreExecute() {
                swipeRefreshLayout.setRefreshing(showRefresh);
            }

            @Override
            protected List<Spot> doInBackground(Void... params) {

                LookAppService las = LookAppService.getInstance();
                try {
                    return las.getSpotList();
                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Spot> spots) {
                swipeRefreshLayout.setRefreshing(false);
                if(spots!=null){
                    app.setSpotList(spots);
                    adapter.setSpotList(spots);
                    adapter.notifyDataSetChanged();
                    AvatarLookAppTask avatarDownloadTask = new AvatarLookAppTask();
                    avatarDownloadTask.addAvatarDownloadListener(SpotListFragment.this);
                    avatarDownloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SPOT_DETAILS_ACTIVITY_CODE){
            logger.d("SpotDetails finished, refreshing list");
            downloadSpotList(true);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(activity, SpotDetailsActivity.class);
        i.putExtra("spotId", app.getSpotList().get(position).getSpotId());
        startActivityForResult(i, SPOT_DETAILS_ACTIVITY_CODE);
    }

    @Override
    public void onAvatarDownloaded() {
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onSearchClick() {
        if(rootView.findViewById(R.id.spot_search_main_view).getVisibility() == View.VISIBLE){
            rootView.findViewById(R.id.spot_search_main_view).setVisibility(View.GONE);
        }else {
            rootView.findViewById(R.id.spot_search_main_view).setVisibility(View.VISIBLE);
        }
    }
}
