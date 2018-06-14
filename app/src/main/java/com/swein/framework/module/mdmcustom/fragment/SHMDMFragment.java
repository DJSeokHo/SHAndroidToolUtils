package com.swein.framework.module.mdmcustom.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.swein.framework.module.mdmcustom.actionbar.SHMDMActionBarViewHolder;
import com.swein.framework.module.mdmcustom.api.SHMDMDeviceManager;
import com.swein.framework.tools.util.eventsplitshot.eventcenter.EventCenter;
import com.swein.framework.tools.util.eventsplitshot.subject.ESSArrows;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.timer.TimerUtil;
import com.swein.shandroidtoolutils.R;

import java.util.HashMap;
import java.util.Timer;


/**
 *
 * screen capture document
 * https://developer.android.com/work/overview
 * https://developer.android.com/work/dpc/build-dpc
 * https://developer.android.com/guide/topics/admin/device-admin
 * https://developer.android.com/reference/android/app/admin/DevicePolicyManager
 * https://developer.android.com/reference/android/app/admin/DevicePolicyManager#setscreencapturedisabled
 * http://android.xsoftlab.net/reference/android/app/admin/DevicePolicyManager.html
 * https://developer.android.com/reference/android/app/admin/DevicePolicyManager#EXTRA_PROVISIONING_SKIP_ENCRYPTION
 *
 *
 *
 */
public class SHMDMFragment extends Fragment {

    private final static String TAG = "SHMDMFragment";

    private Timer timer;

    private CardView cardViewCheck;
    private TextView textViewCheck;
    private ImageButton imageButtonCheck;

    private View.OnClickListener checkOnClickListener;

    private FrameLayout progressBar;

    private ImageView imageViewNoCapture;
    private ImageView imageViewNoPhoto;

    private SHMDMActionBarViewHolder shmdmActionBarViewHolder;

    private SHMDMDeviceManager shmdmDeviceManager;

    public SHMDMFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shmdmDeviceManager = new SHMDMDeviceManager(getContext());
        initEventObserver();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sh_mdm, container, false);

        // actionbar view holder
        FrameLayout frameLayoutActionBar = rootView.findViewById(R.id.frameLayoutActionBar);
        shmdmActionBarViewHolder = new SHMDMActionBarViewHolder(getActivity());

        frameLayoutActionBar.addView(shmdmActionBarViewHolder.getActionBarView());

        findView(rootView);


        return rootView;
    }

    private void initEventObserver() {

        EventCenter.getInstance().addEventObserver(ESSArrows.ESS_DEVICE_MANAGER_USABLE, this, new EventCenter.EventRunnable() {
            @Override
            public void run(String arrow, Object poster, HashMap<String, Object> data) {
                ThreadUtil.startUIThread(0, new Runnable() {
                    @Override
                    public void run() {
                        checkState();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        EventCenter.getInstance().addEventObserver(ESSArrows.ESS_DEVICE_MANAGER_DISABLE, this, new EventCenter.EventRunnable() {
            @Override
            public void run(String arrow, Object poster, HashMap<String, Object> data) {
                ThreadUtil.startUIThread(0, new Runnable() {
                    @Override
                    public void run() {
                        checkState();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        EventCenter.getInstance().addEventObserver(ESSArrows.ESS_DEVICE_DISABLE_CAMERA, this, new EventCenter.EventRunnable() {
            @Override
            public void run(String arrow, Object poster, HashMap<String, Object> data) {
                ThreadUtil.startUIThread(0, new Runnable() {
                    @Override
                    public void run() {
                        checkLimit();
                    }
                });
            }
        });

        EventCenter.getInstance().addEventObserver(ESSArrows.ESS_DEVICE_ENABLE_CAMERA, this, new EventCenter.EventRunnable() {
            @Override
            public void run(String arrow, Object poster, HashMap<String, Object> data) {
                ThreadUtil.startUIThread(0, new Runnable() {
                    @Override
                    public void run() {
                        checkLimit();
                    }
                });
            }
        });

        EventCenter.getInstance().addEventObserver(ESSArrows.ESS_DEVICE_DISABLE_SCREEN_CAPTURE, this, new EventCenter.EventRunnable() {
            @Override
            public void run(String arrow, Object poster, HashMap<String, Object> data) {
                ThreadUtil.startUIThread(0, new Runnable() {
                    @Override
                    public void run() {
                        checkLimit();
                    }
                });
            }
        });

        EventCenter.getInstance().addEventObserver(ESSArrows.ESS_DEVICE_ENABLE_SCREEN_CAPTURE, this, new EventCenter.EventRunnable() {
            @Override
            public void run(String arrow, Object poster, HashMap<String, Object> data) {
                ThreadUtil.startUIThread(0, new Runnable() {
                    @Override
                    public void run() {
                        checkLimit();
                    }
                });
            }
        });

    }


    private void findView(View rooView) {

        progressBar = rooView.findViewById(R.id.progressBar);

        cardViewCheck = rooView.findViewById(R.id.cardViewCheck);
        textViewCheck = rooView.findViewById(R.id.textViewCheck);
        imageButtonCheck = rooView.findViewById(R.id.imageButtonCheck);

        imageViewNoCapture = rooView.findViewById(R.id.imageViewNoCapture);
        imageViewNoPhoto = rooView.findViewById(R.id.imageViewNoPhoto);

        checkState();
        checkLimit();

        checkOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setState();
            }
        };

        imageButtonCheck.setOnClickListener(checkOnClickListener);
        cardViewCheck.setOnClickListener(checkOnClickListener);
    }

    private void setState() {

        if(shmdmDeviceManager.isActive()) {
            removeDeviceManage();
        }
        else {
            addDeviceManage();
        }

    }

    private void checkLimit() {

        if(shmdmDeviceManager.getCamera()) {
            imageViewNoPhoto.setVisibility(View.VISIBLE);
        }
        else {
            imageViewNoPhoto.setVisibility(View.GONE);
        }

        if(shmdmDeviceManager.getScreenCapture()) {
            imageViewNoCapture.setVisibility(View.VISIBLE);
        }
        else {
            imageViewNoCapture.setVisibility(View.GONE);
        }
    }

    private void checkState() {

        if(shmdmDeviceManager.isActive()) {
            textViewCheck.setText("퇴근하기");
            imageButtonCheck.setImageResource(R.drawable.img_btn_work);
            shmdmActionBarViewHolder.showUnderControl();
            setTimerTask();
        }
        else {
            textViewCheck.setText("출근하기");
            imageButtonCheck.setImageResource(R.drawable.img_btn_not_work);
            shmdmActionBarViewHolder.hideUnderControl();

            cancelTimerTask();

            checkLimit();
        }
    }

    private void removeDeviceManage() {

        progressBar.setVisibility(View.VISIBLE);

        ThreadUtil.startThread(new Runnable() {
            @Override
            public void run() {
                shmdmDeviceManager.removeActivate();
            }
        });
    }

    private void addDeviceManage() {

        progressBar.setVisibility(View.VISIBLE);

        ThreadUtil.startThread(new Runnable() {
            @Override
            public void run() {
                shmdmDeviceManager.activate();
            }
        });
    }

    private void setTimerTask() {

        timer = TimerUtil.createTimerTask(0, 1500, new Runnable() {
            @Override
            public void run() {
                shmdmActionBarViewHolder.rotateUnderControlView();
            }
        });
    }



    private void cancelTimerTask() {
        if(timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onDestroy() {
        EventCenter.getInstance().removeAllObserver(this);
        cancelTimerTask();
        super.onDestroy();
    }
}
