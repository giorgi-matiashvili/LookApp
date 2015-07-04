package com.lookapp.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lookapp.R;
import com.lookapp.Tasks.AvatarLookAppTask;
import com.lookapp.adapters.AuthorizedDrawerAdapter;
import com.lookapp.fragments.CustomFragment;
import com.lookapp.fragments.FavouritesFragment;
import com.lookapp.fragments.SpotListFragment;

/**
 * Created by user on 02/07/2015.
 */
public class AuthorizedActivity extends CustomActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CustomFragment[] drawerFragments;
    private AvatarLookAppTask avatarDownloadTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized);

        avatarDownloadTask = new AvatarLookAppTask();
        initDrawerFragments();
        initDrawerLayout();
        showFragment(drawerFragments[1]);
        avatarDownloadTask.execute();

    }

    @Override
    public void onBackPressed() {

    }

    private void initDrawerFragments() {


        drawerFragments = new CustomFragment[2];

        FavouritesFragment favouritesFragment = new FavouritesFragment();
        drawerFragments[0] = favouritesFragment;

        SpotListFragment spotListFragment = new SpotListFragment();
        avatarDownloadTask.addAvatarDownloadListener(spotListFragment);
        drawerFragments[1] = spotListFragment;

    }

    private void initDrawerLayout() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new AuthorizedDrawerAdapter(getLayoutInflater()));
        mDrawerList.setOnItemClickListener(new AuthorizedDrawerClickListener());

    }

    /* The click listner for ListView in the navigation drawer */
    private class AuthorizedDrawerClickListener implements ListView.OnItemClickListener {
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
