package com.lookapp.api;

import android.util.Log;

import com.lookapp.App;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.Spot;
import com.lookapp.listeners.LoginDataDownloadListener;
import com.lookapp.support.LookAppTask;
import com.lookapp.support.LookAppService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class RequestInvoker {

    public static final String DATA_SUCCESSFULLY_LOADED = "DATA_HAS_LOADED";
    public static final String ACTION_STARTUP_COMPLETED = "ACTION_STARTUP_COMPLETED";
    public static final String ACTION_CONTACT_INFO_LOADED = "ACTION_CONTACT_INFO_LOADED";
    private static RequestInvoker instance;
    private static final int DOWNLOAD_TRY_LIMIT = 5;
    private App app;
    private LookAppTask<List<Spot>> spotListDownloaderTask;
    private int downloadCounter = 0;


    public static RequestInvoker getInstance() {
        if (instance == null) {
            instance = new RequestInvoker();
            instance.app = App.getInstance();
//            instance.mbs = MobileBankService.getInstance();
//            instance.rdc = RuntimeDataCache.getInstance();
        }
        return instance;
    }


    public void onStartup() {
        app.updateLocale();
//        if (isTaskRunning(startupTask)) {
//            return;
//        }
//        startupTask = new StartupTask();
//        startupTask.run();
        downloadSpotList();
    }

    private void downloadSpotList() {
        spotListDownloaderTask = new LookAppTask<List<Spot>>() {
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
                if(exception == null){
                    Log.d("INVOKER", ""+spots.size());
                    downloadCounter = 0;
                    App.getInstance().setSpotList(spots);
                    app.sendBroadcast(ACTION_STARTUP_COMPLETED);
                }else if(downloadCounter < DOWNLOAD_TRY_LIMIT){
                    downloadCounter++;
                    downloadSpotList();
                }
            }
        };
        spotListDownloaderTask.execute();
    }


    public void onLogin(final LoginDataDownloadListener listener) {

        LookAppTask<Void> loginDataDownloadTask = new LookAppTask<Void>() {
            @Override
            protected Void doInBackground(Void... params) {
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

                    app.setFavouritesList(spots);


                } catch (LookAppException e) {
                    exception = e;
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(exception == null){
                    listener.onLoginDataDownloaded(true);
                }else {
                    listener.onLoginDataDownloaded(false);
                }
            }
        };
        loginDataDownloadTask.execute();

    }
}
