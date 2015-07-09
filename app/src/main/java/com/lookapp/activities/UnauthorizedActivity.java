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
import com.lookapp.adapters.UnauthorizedDrawerAdapter;
import com.lookapp.fragments.CustomFragment;
import com.lookapp.fragments.FavouritesFragment;
import com.lookapp.fragments.LanguageChangeFragment;
import com.lookapp.fragments.LoginFragment;
import com.lookapp.fragments.SpotListFragment;
import com.lookapp.listeners.SearchClickListener;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class UnauthorizedActivity extends CustomActivity{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CustomFragment[] drawerFragments;
    private AvatarLookAppTask avatarDownloadTask;
    private SearchClickListener searchListener;
    private UnauthorizedDrawerAdapter drawerAdapter;
//    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unauthorized);
        avatarDownloadTask = new AvatarLookAppTask();
        initDrawerFragments();
        initDrawerLayout();
        showFragment(drawerFragments[2]);
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

    private void addSearchListener(SearchClickListener listener) {
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

    private void initDrawerFragments() {


        drawerFragments = new CustomFragment[4];


        LoginFragment loginFragment = new LoginFragment();
        drawerFragments[0] = loginFragment;

        FavouritesFragment favouritesFragment = new FavouritesFragment();
        drawerFragments[1] = favouritesFragment;

        SpotListFragment spotListFragment = new SpotListFragment();
        addSearchListener(spotListFragment);
        avatarDownloadTask.addAvatarDownloadListener(spotListFragment);
        drawerFragments[2] = spotListFragment;

        LanguageChangeFragment languageChangeFragment = new LanguageChangeFragment();

        drawerFragments[3] = languageChangeFragment;



    }

    private void initDrawerLayout() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        drawerAdapter = new UnauthorizedDrawerAdapter(getLayoutInflater());
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setOnItemClickListener(new OnUnauthorizedDrawerClickListener());

    }


    /* The click listner for ListView in the navigation drawer */
    private class OnUnauthorizedDrawerClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showSearch(false);
            drawerAdapter.setCheckedPosition(position);
            drawerAdapter.notifyDataSetChanged();
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
                    showSearch(true);
                    break;
                }
                case 3:{
                    showFragment(drawerFragments[3]);
                    break;
                }
            }

            mDrawerLayout.closeDrawer(mDrawerList);

        }
    }

    private void showSearch(boolean show) {
        actionBar.getCustomView().findViewById(R.id.action_search).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showFragment(CustomFragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }


}
