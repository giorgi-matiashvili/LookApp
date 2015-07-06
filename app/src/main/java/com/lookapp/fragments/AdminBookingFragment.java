package com.lookapp.fragments;

import android.app.Activity;
import android.content.Intent;
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
import com.lookapp.activities.AnswerBookingActivity;
import com.lookapp.adapters.BookingListAdapter;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.api.request.ReserveRequest;
import com.lookapp.bean.SpotForAdmin;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 05/07/2015.
 */
public class AdminBookingFragment extends CustomFragment implements AdapterView.OnItemClickListener{

    private View rootView;
    private ListView bookingList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BookingListAdapter adapter;
    private SpotForAdmin spotForAdmin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin_booking, container, false);

        bookingList = (ListView) rootView.findViewById(R.id.booking_list);
        bookingList.setOnItemClickListener(this);
        adapter = new BookingListAdapter(new ArrayList<ReserveRequest>(), activity.getLayoutInflater());



        bookingList.setAdapter(adapter);
        createSwipeRefresh(rootView);

        downloadBookingList(true);

        return rootView;
    }

    private void createSwipeRefresh(View layout) {
        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.booking_list_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                logger.i("swipe refresh");
                downloadBookingList(true);
            }
        });
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    private void downloadBookingList(final boolean showRefresh) {
        LookAppTask<List<ReserveRequest>> task = new LookAppTask<List<ReserveRequest>>() {

            @Override
            protected void onPreExecute() {
                swipeRefreshLayout.setRefreshing(showRefresh);
            }

            @Override
            protected List<ReserveRequest> doInBackground(Void... params) {

                LookAppService las = LookAppService.getInstance();
                try {
                    spotForAdmin = las.getSpotForAdmin(app.getAdminSpotId());
                    return las.getBookingInfos(app.getAdminSpotId());
                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<ReserveRequest> bookingInfos) {
                swipeRefreshLayout.setRefreshing(false);
                if(bookingInfos!=null){
                    adapter.setBookingInfos(bookingInfos);
                    adapter.notifyDataSetChanged();

                }
            }
        };

        task.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(getActivity(), AnswerBookingActivity.class);
        Bundle b = new Bundle();

        b.putLong("spotId", spotForAdmin.getSpotId());
        b.putString("spotName", spotForAdmin.getSpotName());
        b.putString("number", adapter.getItem(i).getNumber());

        intent.putExtras(b);
        startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                downloadBookingList(true);
            }
        }
    }
}
