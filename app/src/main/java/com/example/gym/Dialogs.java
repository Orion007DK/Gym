package com.example.gym;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Dialogs {

    public static void informationConfirmDialog(String title, String message, Context context){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setTitle(title)
                    .setPositiveButton(R.string.InformationDialogPositiveButtonOk, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .create();

            AlertDialog dialog = builder.show();
    }

    public static void noNetworkFinishDialog(final Context context, final Activity activity){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.DialogsNoNetworkDialogMessage)
                .setCancelable(false)
                .setTitle(R.string.DialogsNoNetworkDialogTitle)
                .setPositiveButton(R.string.InformationDialogPositiveButtonOk, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.finish();
                    }
                })
                .create();

        AlertDialog dialog = builder.show();
    }

    public static void noNetworkDialog(final Context context){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.DialogsNoNetworkDialogMessage)
                .setCancelable(false)
                .setTitle(R.string.DialogsNoNetworkDialogTitle)
                .setPositiveButton(R.string.InformationDialogPositiveButtonOk, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create();

        AlertDialog dialog = builder.show();
    }
}
