package com.lookapp.support;


import android.os.AsyncTask;

import com.lookapp.api.exception.LookAppException;
import com.lookapp.utils.AppLogger;

public abstract  class LookAppTask<Result> extends AsyncTask<Void, Void, Result> {
    private static AppLogger logger = AppLogger.getLogger(LookAppTask.class);

    protected LookAppException exception;

    public LookAppTask() {
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
