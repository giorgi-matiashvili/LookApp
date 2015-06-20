package com.lookapp.utils;

/**
 * Created by Giorgi on 6/20/2015.
 */
import android.util.Log;

public class AppLogger {
    private static final String DEFAULT_LOG_TAG = "LookAppLogger";

    private String tag = DEFAULT_LOG_TAG;

    private AppLogger(String tag) {
        this.tag = tag;
    }

    public static AppLogger getLogger(Class<?> clazz) {
        return new AppLogger(clazz.getSimpleName());
    }

    public void e(Object message) {
        Log.e(tag, toStringMessage(message));
    }

    public void e(Object message, Throwable t) {
        Log.e(tag, toStringMessage(message), t);
    }

    public void e(Throwable t) {
        Log.e(tag, null, t);
    }

    public void d(Object message) {
        Log.d(tag, toStringMessage(message));
    }

    public void d(Object message, Throwable t) {
        Log.d(tag, toStringMessage(message), t);
    }

    public void i(Object message) {
        Log.i(tag, toStringMessage(message));
    }

    public void i(Object message, Throwable t) {
        Log.i(tag, toStringMessage(message), t);
    }

    public void wtf(Object message) {
        Log.wtf(tag, toStringMessage(message));
    }

    public void wtf(Object message, Throwable t) {
        Log.wtf(tag, toStringMessage(message), t);
    }

    public void w(Object message) {
        Log.w(tag, toStringMessage(message));
    }

    public void w(Object message, Throwable t) {
        Log.w(tag, toStringMessage(message), t);
    }

    private String toStringMessage(Object message) {
        if (message != null) {
            return message.toString();
        }
        return "";
    }

}