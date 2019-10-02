package com.swein.framework.template.tabcustomslidinglayout.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.swein.shandroidtoolutils.R;


public class SubOneFragment extends Fragment {

    private View rootView;

    public SubOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_sub_one, container, false);
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
