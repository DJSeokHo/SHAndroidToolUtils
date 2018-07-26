package com.swein.framework.module.phonecallrecoder.phonestatelistener;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.TelephonyManager;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.device.DeviceInfoUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PhoneCallStateListener extends android.telephony.PhoneStateListener {

    private final static String TAG = "PhoneCallStateListener";

    // save path
    private static final String FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/PhoneCallRecorders";

    // incoming phone
    private String incomingPhoneNumber;

    private MediaRecorder mediaRecorder;

    private Context context;


    public PhoneCallStateListener(Context context) {
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        ILog.iLogDebug(TAG, "state : " + state + " Number : " + incomingNumber);

        switch (state) {

            case TelephonyManager.CALL_STATE_RINGING: // ringing state can get incoming phone number
                incomingPhoneNumber = incomingNumber;
                break;

            case TelephonyManager.CALL_STATE_OFFHOOK: // talking state, record start here
                record();
                break;

            case TelephonyManager.CALL_STATE_IDLE: // idle state, record stop
                release();
                break;

        }
    }

    private void record() {

        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
        }

        if (incomingPhoneNumber == null) {
            /*
                when you call some one can not get other side number
                put your number here
             */
            incomingPhoneNumber = DeviceInfoUtil.getNativePhoneNumber(context);
        }

        File directory = new File(FILE_PATH);

        if (!directory.exists()) {
            directory.mkdir();
        }

        String data = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_sss", Locale.getDefault()).format(new Date());
        File file = new File(FILE_PATH + File.separator + incomingPhoneNumber + data + ".3gp");


        try {

            /*
                if your phone not support MediaRecorder.AudioSource.VOICE_CALL
                change it to MediaRecorder.AudioSource.VOICE_COMMUNICATION in catch block
             */
            mediaRecorder.reset();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(file.getAbsolutePath());

            try {
                mediaRecorder.prepare();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();

            ILog.iLogDebug(TAG, "start recording with VOICE_CALL");

        }
        catch (Exception exception) {

          /*
                if your phone not support MediaRecorder.AudioSource.VOICE_CALL
                change it to MediaRecorder.AudioSource.VOICE_COMMUNICATION in catch block
                ex: samsung, LG, ...
             */
            mediaRecorder.reset();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(file.getAbsolutePath());

            try {
                mediaRecorder.prepare();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();

            ILog.iLogDebug(TAG, "start recording with VOICE_COMMUNICATION");
        }


    }

    private void release() {

        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }

        ILog.iLogDebug(TAG, "stop recording");
    }

}
