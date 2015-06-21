package com.lookapp.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lookapp.R;
import com.lookapp.Tasks.AvatarDownloadTask;
import com.lookapp.adapters.UnauthorizedDrawerAdapter;
import com.lookapp.fragments.CustomFragment;
import com.lookapp.fragments.SpotListFragment;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class UnauthorizedActivity extends CustomActivity{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CustomFragment[] drawerFragments;
    private AvatarDownloadTask avatarDownloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unauthorized);
        avatarDownloadTask = new AvatarDownloadTask();

        initDrawerFragments();
        initDrawerLayout();
        showFragment(drawerFragments[1]);
        avatarDownloadTask.execute();

    }

    private void initDrawerFragments() {
        drawerFragments = new CustomFragment[2];
        SpotListFragment spotListFragment = new SpotListFragment();
        avatarDownloadTask.addAvatarDownloadListener(spotListFragment);
        drawerFragments[1] = spotListFragment;


    }

    private void initDrawerLayout() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new UnauthorizedDrawerAdapter(getLayoutInflater()));
        mDrawerList.setOnItemClickListener(new OnUnauthorizedDrawerClickListener());

    }


    /* The click listner for ListView in the navigation drawer */
    private class OnUnauthorizedDrawerClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:{
//                    showFragment(new );
                    break;
                }
                case 1:{
                    showFragment(drawerFragments[1]);
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
