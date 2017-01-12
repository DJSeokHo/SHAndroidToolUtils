package com.swein.framework.tools.util.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

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


    public static ProgressDialog createProgressDialog(Context context, String msg, boolean cancelAble, boolean canceledOnTouchOutside){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(cancelAble);
        progressDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        progressDialog.show();
        return progressDialog;
    }

    public static void createCustomListViewDialog(Context context , String title , String positiveBtnTitle , String negativeBtnTitle , ListAdapter listAdapter , final Runnable onPositiveBtnClicked , final Runnable onNegativeBtnClicked , int textColor, int padding) {

        if (textColor == -1) {
            textColor = Color.WHITE;
        }

        AlertDialog.Builder builderSingle = new AlertDialog.Builder( context );
        builderSingle.setIcon( null );
        builderSingle.setCancelable(false);

        TextView titleTv = new TextView( context );
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        titleTv.setPadding( padding , padding , padding , padding );
        titleTv.setLayoutParams(lp);
        titleTv.setText(title);
        titleTv.setTextColor(textColor);
        titleTv.setTypeface(null, Typeface.BOLD);
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        builderSingle.setCustomTitle(titleTv);

        builderSingle.setNegativeButton(negativeBtnTitle, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onNegativeBtnClicked != null) onNegativeBtnClicked.run();
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(listAdapter, null);
        builderSingle.setPositiveButton(positiveBtnTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (onPositiveBtnClicked != null) onPositiveBtnClicked.run();
                dialog.dismiss();
            }
        });

        builderSingle.show();
    }

}
