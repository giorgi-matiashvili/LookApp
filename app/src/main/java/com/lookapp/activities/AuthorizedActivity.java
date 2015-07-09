package com.lookapp.activities;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lookapp.R;
import com.lookapp.Tasks.AvatarLookAppTask;
import com.lookapp.adapters.AuthorizedDrawerAdapter;
import com.lookapp.adapters.AuthorizedDrawerAdminAdapter;
import com.lookapp.fragments.AdminBookingFragment;
import com.lookapp.fragments.AdminFragment;
import com.lookapp.fragments.CustomFragment;
import com.lookapp.fragments.FavouritesFragment;
import com.lookapp.fragments.SpotListFragment;
import com.lookapp.listeners.SearchClickListener;

/**
 * Created by user on 02/07/2015.
 */
public class AuthorizedActivity extends CustomActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CustomFragment[] drawerFragments;
    private AvatarLookAppTask avatarDownloadTask;
    private ActionBar actionBar;
    private SearchClickListener searchListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized);

        avatarDownloadTask = new AvatarLookAppTask();
        initDrawerFragments();
        initDrawerLayout();
        showFragment(drawerFragments[1]);
        avatarDownloadTask.execute();

        actionBar = getCustomLayoutActionBar();
        showActionBarToggle(actionBar, true);
        addToggleListener();
        addSearchClickListener();
        showSearch(true);
    }

    private void addSearchClickListener() {
        actionBar.getCustomView().findViewById(R.id.action_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchListener.onSearchClick();
            }
        });
    }
    private void showSearch(boolean show) {
        actionBar.getCustomView().findViewById(R.id.action_search).setVisibility(show ? View.VISIBLE : View.GONE);
    }


    private void setSearchListener(SearchClickListener listener) {
        this.searchListener = listener;
    }

    private void addToggleListener() {
        actionBar.getCustomView().findViewById(R.id.custom_action_bar_toggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void initDrawerFragments() {

        SpotListFragment searchFragment = null;

        if(app.getAdminSpotId() < 0){
            drawerFragments = new CustomFragment[2];

            FavouritesFragment favouritesFragment = new FavouritesFragment();
            drawerFragments[0] = favouritesFragment;

            SpotListFragment spotListFragment = new SpotListFragment();
            searchFragment = spotListFragment;
            avatarDownloadTask.addAvatarDownloadListener(spotListFragment);
            drawerFragments[1] = spotListFragment;
        }else{
            drawerFragments = new CustomFragment[4];

            FavouritesFragment favouritesFragment = new FavouritesFragment();
            drawerFragments[0] = favouritesFragment;

            SpotListFragment spotListFragment = new SpotListFragment();
            searchFragment = spotListFragment;
            avatarDownloadTask.addAvatarDownloadListener(spotListFragment);
            drawerFragments[1] = spotListFragment;

            AdminFragment adminFragment = new AdminFragment();
            drawerFragments[2] = adminFragment;

            AdminBookingFragment adminBookingFragment = new AdminBookingFragment();
            drawerFragments[3] = adminBookingFragment;
        }

        setSearchListener(searchFragment);

    }

    private void initDrawerLayout() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        if(app.getAdminSpotId()<0){
            mDrawerList.setAdapter(new AuthorizedDrawerAdapter(getLayoutInflater()));
            mDrawerList.setOnItemClickListener(new AuthorizedDrawerClickListener());
        }else {
            mDrawerList.setAdapter(new AuthorizedDrawerAdminAdapter(getLayoutInflater()));
            mDrawerList.setOnItemClickListener(new AuthorizedDrawerAdminClickListener());
        }

    }

    /* The click listner for ListView in the navigation drawer */
    private class AuthorizedDrawerClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showSearch(false);
            switch (position){
                case 0:{
                    showFragment(drawerFragments[0]);
                    break;
                }
                case 1:{
                    showSearch(true);
                    showFragment(drawerFragments[1]);
                    break;
                }
                case 2:{
                    app.setIsLoggedIn(false);
                    finish();
                    break;
                }
            }

            mDrawerLayout.closeDrawer(mDrawerList);

        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class AuthorizedDrawerAdminClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:{
                    showFragment(drawerFragments[0]);
                    break;
                }
                case 1:{
                    showFragment(drawerFragments[1]);
                    break;
                }
                case 2:{
                    showFragment(drawerFragments[2]);
                    break;
                }case 3:{
                    showFragment(drawerFragments[3]);
                    break;
                }

                case 4:{
                    app.setIsLoggedIn(false);
                    finish();
                    break;
                }
            }

            mDrawerLayout.closeDrawer(mDrawerList);

        }
    }

    private void showFragment(CustomFragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }


}
