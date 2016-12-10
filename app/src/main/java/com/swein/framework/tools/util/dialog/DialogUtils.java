package com.swein.framework.tools.util.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListAdapter;

/**
 * Created by seokho on 17/11/2016.
 */

public class DialogUtils {

    static int progressState = 0;

    public static void createNormalDialogWithOneButton(Context context, String title, String message, boolean cancelAble, String positiveButtonText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        if(title == null || title.length() == 0) {

        }

        if(message == null || message.length() == 0) {

        }

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public static void createNormalDialogWithTwoButton(Context context, String title, String message, boolean cancelAble, String positiveButtonText, String negativeButtonText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        if(title == null || title.length() == 0) {

        }

        if(message == null || message.length() == 0) {

        }

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.dismiss();
                    }
                })
                .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public static void createNormalDialogWithThreeButton(Context context, String title, String message, boolean cancelAble, String positiveButtonText, String negativeButtonText, String otherButtonText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        if(title == null || title.length() == 0) {

        }

        if(message == null || message.length() == 0) {

        }

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.dismiss();
                    }
                })
                .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .setNeutralButton(otherButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                    }
                } );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public static void createListItemDialogWithTwoButton(final Context context, String title, ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener, boolean cancelAble, String positiveButtonText, String negativeButtonText) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title);

        alertDialogBuilder
                .setCancelable(cancelAble)
                .setAdapter(listAdapter, onClickListener)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.dismiss();
                    }
                })
                .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


}
