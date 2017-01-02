package com.swein.framework.tools.util.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.framework.tools.util.debug.log.ILog;

/**
 * Created by seokho on 01/01/2017.
 */

public class FragmentUtils {

    public static View inflateFragment(LayoutInflater inflater, int layoutResource, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, boolean attachToRoot) {
        View rootView = inflater.inflate(layoutResource, container, attachToRoot);
        return rootView;
    }

    public static void replaceFragmentv4Commit(android.support.v4.app.FragmentActivity activity, Fragment fragment, int containerViewId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerViewId, fragment).commit();
    }

    public void replaceFragmentv4CommitWithBundle(android.support.v4.app.FragmentActivity activity, Fragment fragment, int containerViewId, Bundle bundle) {

        if(null == bundle) {
            ILog.iLogException(FragmentUtils.class.getName(), "Input bundle first");
            return;
        }

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(containerViewId, fragment).commit();

    }

    public static String getStringBundleDataFromActivity(Fragment fragment, String key) {
        Bundle bundle = new Bundle();
        bundle = fragment.getArguments();
        if(null == bundle) {
            ILog.iLogException(FragmentUtils.class.getName(), "Bundle is null");
            return "Error: Bundle is null";
        }
        return bundle.getString(key);
    }

    public void replaceFragmentv4CommitWithAddToBackStack(android.support.v4.app.FragmentActivity activity, Fragment fragment, int containerViewId) {

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void replaceFragmentv4CommitWithBundleWithAddToBackStack(android.support.v4.app.FragmentActivity activity, Fragment fragment, int containerViewId, Bundle bundle) {

        if(null == bundle) {
            Log.e(FragmentUtils.class.getName(), "Input bundle first");
            return;
        }

        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
