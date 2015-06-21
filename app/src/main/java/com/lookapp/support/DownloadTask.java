package com.lookapp.support;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.lookapp.api.exception.LookAppException;
import com.lookapp.utils.AppLogger;

public abstract  class DownloadTask<Result> extends AsyncTask<Void, Void, Result> {
    private static AppLogger logger = AppLogger.getLogger(DownloadTask.class);

    protected LookAppException exception;

    public DownloadTask() {
    }




    @Override
    protected void onPreExecute() {

    }

    @Override
    protected  abstract Result doInBackground(Void... params);


    @Override
    protected void onPostExecute(Result result) {

    }

}
