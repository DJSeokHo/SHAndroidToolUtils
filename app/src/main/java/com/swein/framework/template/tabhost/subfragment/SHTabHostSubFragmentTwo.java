package com.swein.framework.template.tabhost.subfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.shandroidtoolutils.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SHTabHostSubFragmentTwo extends Fragment {


    public SHTabHostSubFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shtab_host_sub_fragment_two, container, false);
    }

}
