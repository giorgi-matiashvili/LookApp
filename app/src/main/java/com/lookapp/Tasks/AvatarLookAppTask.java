package com.lookapp.Tasks;



import android.util.Base64;

import com.lookapp.App;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.Spot;
import com.lookapp.listeners.AvatarDownloadListener;
import com.lookapp.support.LookAppTask;
import com.lookapp.support.LookAppService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giorgi on 6/21/2015.
 */
public class AvatarLookAppTask extends LookAppTask<Void> {

    private List<AvatarDownloadListener> listeners = new ArrayList<AvatarDownloadListener>();


    public void addAvatarDownloadListener(AvatarDownloadListener listener){
        listeners.add(listener);
    }


    @Override
    protected Void doInBackground(Void... params) {
        LookAppService las = LookAppService.getInstance();
        List<Spot> spots = App.getInstance().getSpotList();

        for(Spot s:spots){
            try{
                s.setAvatar(Base64.decode(las.getAvatar(s.getSpotId()),Base64.DEFAULT));
                publishProgress();
            }catch (LookAppException e){
                exception = e;
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        for(AvatarDownloadListener l:listeners){
            if(l!=null){
                l.onAvatarDownloaded();
            }
        }
    }
}
