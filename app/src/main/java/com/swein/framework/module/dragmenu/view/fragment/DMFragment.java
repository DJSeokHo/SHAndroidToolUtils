package com.swein.framework.module.dragmenu.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;


public class DMFragment extends Fragment {

    private TextView textViewDragMenuFragment;


    public static DMFragment newInstance() {

        DMFragment fragment = new DMFragment();
        return fragment;
    }

    public DMFragment() {}

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_drag_menu, container, false);


        textViewDragMenuFragment = (TextView) rootView.findViewById(R.id.textViewDragMenuFragment);

        textViewDragMenuFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShortToastNormal(getContext(), textViewDragMenuFragment.getText());
            }
        });

        return rootView;
    }


}
