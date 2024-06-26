package com.swein.framework.tools.util.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.framework.tools.util.debug.log.ILog;

/**
 * Created by seokho on 01/01/2017.
 */

public class FragmentUtil {

    public static View inflateFragment(LayoutInflater inflater, int layoutResource, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, boolean attachToRoot) {
        View rootView = inflater.inflate(layoutResource, container, attachToRoot);
        return rootView;
    }

    public static void replaceFragmentv4Commit(FragmentActivity activity, Fragment fragment, int containerViewId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerViewId, fragment).commit();
    }

    public static void replacePreferenceFragmentCommit(Activity activity, PreferenceFragment fragment, int containerViewId) {
        activity.getFragmentManager().beginTransaction().replace(containerViewId, fragment).addToBackStack(null).commit();
    }

    public void replaceFragmentv4CommitWithBundle(FragmentActivity activity, Fragment fragment, int containerViewId, Bundle bundle) {

        if(null == bundle) {
            ILog.iLogError(FragmentUtil.class.getName(), "Input bundle first");
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
            ILog.iLogError(FragmentUtil.class.getName(), "Bundle is null");
            return "Error: Bundle is null";
        }
        return bundle.getString(key);
    }

    public void replaceFragmentv4CommitWithAddToBackStack(FragmentActivity activity, Fragment fragment, int containerViewId) {

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void replaceFragmentv4CommitWithBundleWithAddToBackStack(FragmentActivity activity, Fragment fragment, int containerViewId, Bundle bundle) {

        if(null == bundle) {
            Log.e(FragmentUtil.class.getName(), "Input bundle first");
            return;
        }

        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
