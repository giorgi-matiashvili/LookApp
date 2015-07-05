package com.lookapp.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lookapp.App;
import com.lookapp.bean.Spot;
import com.lookapp.utils.GsonUtils;
import com.lookapp.utils.Language;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.List;

public class Settings {

    public static final String APP_NAME = "Mobile Bank";
    public static final String PREFS_NAME = APP_NAME + "_PREFS";

    public static final String APP_LANGUAGE = "APP_LANGUAGE";
    public static final String USERNAME = "USERNAME";
    public static final String FAVOURITES = "FAVOURITES";
    public static final String NUMBER_PREFIX = "+995";

    //private static AppLogger logger = AppLogger.getLogger(Settings.class);
    private static SharedPreferences prefs = null;

    static {
        prefs = App.getInstance().getSharedPreferences(PREFS_NAME, 0);
    }

    public static void saveUserName(String userName) {
        Editor editor = prefs.edit();
        editor.putString(USERNAME, userName);
        editor.commit();
    }

    public static String getUserName() {
        return prefs.getString(USERNAME, "");
    }

    public static SharedPreferences getPrefs() {
        return prefs;
    }

    public static void setPrefs(SharedPreferences prefs) {
        Settings.prefs = prefs;
    }

    public static void destroy() {
        Settings.prefs = null;
    }

    public static String getIMEI() {
        return prefs.getString("imei", null);
    }

    public static void setIMEI(String imei) {
        Editor editor = prefs.edit();
        editor.putString("imei", imei);
        editor.commit();
    }

    public static String getStringProperty(String pname, String def) {
        return prefs.getString(pname, def);
    }

    public static void setStringProperty(String pname, String property) {
        Editor editor = prefs.edit();
        editor.putString(pname, property);
        editor.commit();
    }

    public static int getIntProperty(String pname, int def) {
        return prefs.getInt(pname, def);
    }

    public static void setIntProperty(String pname, int property) {
        Editor editor = prefs.edit();
        editor.putInt(pname, property);
        editor.commit();
    }

    public static long getLongProperty(String pname, long def) {
        return prefs.getLong(pname, def);
    }

    public static void setLongProperty(String pname, long property) {
        Editor editor = prefs.edit();
        editor.putLong(pname, property);
        editor.commit();
    }

    public static boolean getBooleanProperty(String pname, boolean def) {
        return prefs.getBoolean(pname, def);
    }

    public static void setBooleanProperty(String pname, boolean property) {
        Editor editor = prefs.edit();
        editor.putBoolean(pname, property);
        editor.commit();
    }


    public static void setLanguage(Language language) {
        Settings.setStringProperty(Settings.APP_LANGUAGE, language.name());
    }

    public static Language getLanguage() {
        String lang = Settings.getStringProperty(Settings.APP_LANGUAGE, Language.KA.name());
        if (lang != null) {
            return Language.valueOf(lang);
        } else {
            return null;
        }
    }

    public static void setFavouritesList(){
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();
        Settings.setStringProperty(Settings.FAVOURITES, gson.toJson(App.getInstance().getFavouritesList()));
        Log.d("favourites", gson.toJson(App.getInstance().getFavouritesList()));
        Log.d("favourites size", "" + Settings.getFavouritesList().size());
    }


    public static List<Spot> getFavouritesList(){
        Gson gson = new Gson();

        String list = Settings.getStringProperty(Settings.FAVOURITES, "[]");
        return gson.fromJson(list, new TypeToken<List<Spot>>() {}.getType());
    }

}
