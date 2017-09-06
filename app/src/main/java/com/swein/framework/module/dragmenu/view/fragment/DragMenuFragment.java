package com.swein.framework.module.dragmenu.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swein.framework.tools.util.toast.ToastUtils;
import com.swein.shandroidtoolutils.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DragMenuFragment extends Fragment {

    private TextView textViewDragMenuFragment;


    public static DragMenuFragment newInstance() {

        DragMenuFragment fragment = new DragMenuFragment();
        return fragment;
    }

    public DragMenuFragment() {}

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_drag_menu, container, false);


        textViewDragMenuFragment = (TextView) rootView.findViewById(R.id.textViewDragMenuFragment);

        textViewDragMenuFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToastNormal(getContext(), textViewDragMenuFragment.getText());
            }
        });

        return rootView;
    }


}
