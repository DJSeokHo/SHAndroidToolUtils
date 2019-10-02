package com.swein.framework.module.filedownloadupload.uploadservice;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;

import com.swein.constants.Constants;
import com.swein.framework.module.filedownloadupload.ftp.FTPManager;
import com.swein.framework.module.filedownloadupload.ftp.delegate.FTPManagerConnectDelegate;
import com.swein.framework.module.filedownloadupload.ftp.delegate.FTPManagerCreateOrChangeDelegate;
import com.swein.framework.module.filedownloadupload.ftp.delegate.FTPManagerUploadFileDelegate;
import com.swein.framework.module.filedownloadupload.networkstate.NetWorkStateReceiver;
import com.swein.framework.module.noticenotification.NoticeNotificationManager;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.eventsplitshot.eventcenter.EventCenter;
import com.swein.framework.tools.util.eventsplitshot.subject.ESSArrows;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

import java.util.HashMap;

/**
 * Created by seokho
 */
public class FileUploadService extends Service {

    private final static String TAG = "FileUploadService";

    private static boolean running;

    public final static String UPLOAD_FILE_PATH_KEY = "UPLOAD_FILE_PATH_KEY";
    public final static String UPLOAD_DIRECTORY_KEY = "UPLOAD_DIRECTORY_KEY";
    public final static String UPLOAD_FILE_NAME_KEY = "UPLOAD_FILE_NAME_KEY";

    private final static int UPLOAD_SERVICE_ID = 1003;


    private String filePath;
    private String directory;
    private String uploadFileName;

    private NetWorkStateReceiver netWorkStateReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        netWorkStateReceiver = new NetWorkStateReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(netWorkStateReceiver, filter);

        startForeground(UPLOAD_SERVICE_ID, NoticeNotificationManager.getInstance()
                .showNonRemovableNotification(this, getString(R.string.app_name), "uploading", R.mipmap.ic_launcher, R.mipmap.ic_launcher));

        checkBundle(intent);

        ThreadUtil.startThread(new Runnable() {
            @Override
            public void run() {
                startUpload();
            }
        });

        running = true;
        return START_NOT_STICKY;
    }

    private void checkBundle(Intent intent) {

        if(intent == null) {
            return;
        }

        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE_KEY);

        if(bundle == null) {
            return;
        }

        filePath = bundle.getString(UPLOAD_FILE_PATH_KEY);
        directory = bundle.getString(UPLOAD_DIRECTORY_KEY);
        uploadFileName = bundle.getString(UPLOAD_FILE_NAME_KEY);

    }

    private void initESS() {
        EventCenter.getInstance().addEventObserver(ESSArrows.NETWORK_DISCONNECTED, this, new EventCenter.EventRunnable() {
            @Override
            public void run(String arrow, Object poster, HashMap<String, Object> data) {
                FTPManager.getInstance().isNetworkDisconnected = true;
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initESS();
        initParam();
    }

    private void removeESS() {
        EventCenter.getInstance().removeAllObserver(this);
    }

    @Override
    public void onDestroy() {

        removeESS();
        ILog.iLogDebug(TAG, "onDestroy");
        stopUpload();
        stopForeground(true);

        if(netWorkStateReceiver != null){
            unregisterReceiver(netWorkStateReceiver);
            netWorkStateReceiver = null;
        }

        super.onDestroy();
    }

    public void initParam() {

        HandlerThread serviceThread = new HandlerThread("service_thread", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        serviceThread.start();
        running = false;
    }

    public static boolean isRunning() {
        return running;
    }

    public void startUpload() {

        FTPManager.getInstance().connect(Constants.FTP_DOMAIN, Constants.FTP_PORT, new FTPManagerConnectDelegate() {
            @Override
            public void onSuccess() {

                FTPManager.getInstance().createOrChangeDir(directory, new FTPManagerCreateOrChangeDelegate() {

                    @Override
                    public void onSuccess(String currentDirectory) {

                        final NotificationManager notificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        ThreadUtil.startUIThread(0, new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showShortToastNormal(FileUploadService.this, getString(R.string.upload_start_toast));

                            }
                        });

                        FTPManager.getInstance().uploadFile(filePath, uploadFileName, new FTPManagerUploadFileDelegate() {

                            @Override
                            public void onSuccess() {
                                ILog.iLogDebug(TAG, "uploadFile success");
                                uploadSuccess();
                                FTPManager.getInstance().disconnect();
                                stopThis();
                            }

                            @Override
                            public void onFailed() {
                                FTPManager.getInstance().disconnect();
                                uploadFailed();
                                stopThis();
                            }

                            @Override
                            public void onError() {
                                FTPManager.getInstance().disconnect();
                                uploadFailed();
                                stopThis();
                            }

                            @Override
                            public void onProgress(final String progress) {
                                ILog.iLogDebug(TAG, progress);
                                ThreadUtil.startUIThread(0, new Runnable() {
                                    @Override
                                    public void run() {

                                        NoticeNotificationManager.getInstance().updateNotification(notificationManager, UPLOAD_SERVICE_ID, progress);

                                    }
                                });
                            }
                        });

                    }

                    @Override
                    public void onFailed() {
                        FTPManager.getInstance().disconnect();
                        uploadFailed();
                        stopThis();
                    }

                    @Override
                    public void onError() {
                        FTPManager.getInstance().disconnect();
                        uploadFailed();
                        stopThis();
                    }
                });
            }

            @Override
            public void onFailed() {
                FTPManager.getInstance().disconnect();
                uploadFailed();
                stopThis();
            }

            @Override
            public void onError() {
                FTPManager.getInstance().disconnect();
                uploadFailed();
                stopThis();
            }
        }, Constants.FTP_USER, Constants.FTP_PW);

        running = true;
    }

    private void stopUpload() {
        running = false;
    }

    private void uploadSuccess() {
        ThreadUtil.startUIThread(0, new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToastNormal(FileUploadService.this, getString(R.string.upload_success_toast));
            }
        });
    }

    private void uploadFailed() {
        ThreadUtil.startUIThread(0, new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShortToastNormal(FileUploadService.this, getString(R.string.upload_failed_toast));
            }
        });
    }

    private void stopThis() {

        ThreadUtil.startUIThread(0, new Runnable() {
            @Override
            public void run() {
                NoticeNotificationManager.getInstance().hideNonRemovableNotification(FileUploadService.this, UPLOAD_SERVICE_ID);
            }
        });

        if(FileUploadService.isRunning()) {
            stopSelf();
        }
    }

}
