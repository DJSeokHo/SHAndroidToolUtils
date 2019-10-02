package com.swein.pattern.observer.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.swein.shandroidtoolutils.R;


public class JustFragment extends Fragment {

    private Button buttonChangeActivityContent;
    private Button buttonCloseFragment;

    private View rootView;

    public JustFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_just, container, false);

        findView();
        setBehavior();

        return rootView;
    }

    private void findView(){
        buttonChangeActivityContent = (Button)rootView.findViewById(R.id.buttonChangeActivityContent);
        buttonCloseFragment = (Button)rootView.findViewById(R.id.buttonCloseFragment);
    }

    private void setBehavior(){
        buttonChangeActivityContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // will change activity text content
            }
        });

        buttonCloseFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // will close this fragment
            }
        });
    }
}
