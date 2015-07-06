package com.lookapp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;

import com.lookapp.R;

/**
 * Created by giorgi-matiashvili on 7/6/2015.
 */
public class UiUtils {

    public static void showAlertDialog(String msg, Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getResources().getString(R.string.alert_dialog_ok_btn_text),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

}
