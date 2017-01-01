package com.swein.framework.tools.util.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by seokho on 01/01/2017.
 */

public class FragmentUtils {

    public static View inflateFragment(LayoutInflater inflater, int layoutResource, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, boolean attachToRoot) {
        View rootView = inflater.inflate(layoutResource, container, attachToRoot);
        return rootView;
    }
}
