package com.lookapp.support;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.lookapp.api.exception.LookAppException;
import com.lookapp.utils.AppLogger;

public abstract  class DownloadTask<Result> extends AsyncTask<Void, Void, Result> {
    private static AppLogger logger = AppLogger.getLogger(DownloadTask.class);

    private Dialog dialog;
    protected boolean showDialog = true;
    protected LookAppException exception;

    public DownloadTask() {
    }


    public DownloadTask(boolean showDialog) {
        this.showDialog = showDialog;
    }


    @Override
    protected void onPreExecute() {
        if (showDialog) {
//            dialog = UiUtils.createProgressDialog();
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    DownloadTask.this.cancel(true);
                }
            });
            dialog.show();
        }
    }

    @Override
    protected  abstract Result doInBackground(Void... params);


    @Override
    protected void onPostExecute(Result result) {
        if(dialog != null) {
            dialog.cancel();
        }
    }

}
