package com.lookapp;
import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.lookapp.bean.Spot;
import com.lookapp.settings.Settings;
import com.lookapp.utils.AppLogger;
import com.lookapp.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class App extends Application {

    private static App instance;
    private AppLogger logger;
    private List<Spot> spotList = new ArrayList<>();
    private String sessionId;
    private List<Spot> favouritesList = new ArrayList<>();
    private boolean isLoggedIn;
    private long adminSpotId;
    private String fullName;

    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        Settings.setLanguage(Language.EN);
        logger = AppLogger.getLogger(this.getClass());
        setFavouritesList(Settings.getFavouritesList());
    }

    public List<Spot> getSpotList() {
        return spotList;
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }


    public void sendBroadcast(String action, Bundle params) {
        Log.d("sender", "Broadcasting message. action = " + action);
        Intent intent = new Intent(action);
        if (params != null) {
            intent.putExtras(params);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }




    public void sendBroadcast(String action) {
        sendBroadcast(action, null);
    }

    public void updateLocale() {
        Language lang = Settings.getLanguage();
        String localeStr = lang.name().toLowerCase();
        logger.d("Set locale. lang: " + lang);
        Locale locale = new Locale(localeStr);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<Spot> getFavouritesList() {
        return favouritesList;
    }

    public void setFavouritesList(List<Spot> favouritesList) {
        logger.d("favourites set " + favouritesList.size());
        this.favouritesList = favouritesList;
    }

    public void setIsLoggedIn(boolean isLogedIn) {
        this.isLoggedIn = isLogedIn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setAdminSpotId(long adminSpotId) {

        this.adminSpotId = adminSpotId;
    }

    public long getAdminSpotId() {
        return adminSpotId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}