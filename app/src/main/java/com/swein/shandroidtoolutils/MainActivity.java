package com.swein.shandroidtoolutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.swein.framework.sfa.SAF;
import com.swein.framework.tools.util.debug.log.ILog;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.PendingIntent.getActivity;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);

//        startActivityForResult(SAF.getPerformFileSearchIntent(SAF.MIME_TYPE_ALL_FILE_ALL_TYPE), SAF.SAF_READ_REQUEST_CODE);

//        startActivityForResult(SAF.getCreateFileIntent(SAF.MIME_TYPE_ALL_FILE_ALL_TYPE, "seokho.txt"), SAF.SAF_WRITE_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == SAF.SAF_READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (data != null) {
                uri = data.getData();

                ILog.iLogDebug(this, "Uri: " + uri.toString());

                SAF.dumpUriFileMetaData(this, uri);

            }
        }
        else if (requestCode == SAF.SAF_WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();

                ILog.iLogDebug(this, "Uri: " + uri.toString());

                SAF.dumpUriFileMetaData(this, uri);

                startActivityForResult(SAF.getEditDocumentIntent(SAF.MIME_TYPE_TEXT_FILE_TXT_TYPE), SAF.SAF_EDIT_REQUEST_CODE);

            }
        }
        else if (requestCode == SAF.SAF_EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();

                SAF.alterDocument(this, uri, "lsj");

            }

        }
    }

}
