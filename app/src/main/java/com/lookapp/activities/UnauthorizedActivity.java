package com.lookapp.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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

/**
 * Created by Giorgi on 6/20/2015.
 */
public class UnauthorizedActivity extends CustomActivity{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CustomFragment[] drawerFragments;
    private AvatarLookAppTask avatarDownloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unauthorized);
        avatarDownloadTask = new AvatarLookAppTask();

        initDrawerFragments();
        initDrawerLayout();
        showFragment(drawerFragments[2]);
        avatarDownloadTask.execute();

    }

    private void initDrawerFragments() {


        drawerFragments = new CustomFragment[4];


        LoginFragment loginFragment = new LoginFragment();
        drawerFragments[0] = loginFragment;

        FavouritesFragment favouritesFragment = new FavouritesFragment();
        drawerFragments[1] = favouritesFragment;

        SpotListFragment spotListFragment = new SpotListFragment();
        avatarDownloadTask.addAvatarDownloadListener(spotListFragment);
        drawerFragments[2] = spotListFragment;

        LanguageChangeFragment languageChangeFragment = new LanguageChangeFragment();

        drawerFragments[3] = languageChangeFragment;



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
                }
                case 3:{
                    showFragment(drawerFragments[3]);
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
