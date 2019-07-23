package com.swein.framework.template.tabcustomslidinglayout.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.shandroidtoolutils.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubTwoFragment extends Fragment {

    private View rootView;

    public SubTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_sub_two, container, false);
            // findView
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (null != rootView) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }
}
