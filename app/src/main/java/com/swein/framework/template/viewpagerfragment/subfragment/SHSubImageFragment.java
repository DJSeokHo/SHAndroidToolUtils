package com.swein.framework.template.viewpagerfragment.subfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.swein.framework.tools.picasso.SHPicasso;
import com.swein.shandroidtoolutils.R;


public class SHSubImageFragment extends Fragment {

    private int imageResource;

    public SHSubImageFragment() {
        // Required empty public constructor
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shsub_image, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);

        SHPicasso.getInstance().loadImage(getContext(), imageResource, imageView, false, 0);

        return rootView;
    }

}
