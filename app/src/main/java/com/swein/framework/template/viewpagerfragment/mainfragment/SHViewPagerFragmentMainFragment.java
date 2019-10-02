package com.swein.framework.template.viewpagerfragment.mainfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.swein.framework.template.viewpagerfragment.adapter.SHFragmentViewPager;
import com.swein.framework.template.viewpagerfragment.subfragment.SHSubImageFragment;
import com.swein.framework.tools.util.timer.TimerUtil;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class SHViewPagerFragmentMainFragment extends Fragment {


    private List<Integer> imageResourceList;

    private ViewPager viewPager;



    private Timer timer = null;


    public SHViewPagerFragmentMainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_shview_pager_fragment_main, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        imageResourceList = new ArrayList<>();

        // header
        imageResourceList.add(R.drawable.dsc_05464);

        imageResourceList.add(R.drawable.dsc_04839);
        imageResourceList.add(R.drawable.dsc_04842);
        imageResourceList.add(R.drawable.dsc_05355);
        imageResourceList.add(R.drawable.dsc_05464);

        // end
        imageResourceList.add(R.drawable.dsc_04839);

        List<Fragment> fragmentList = new ArrayList<>();

        SHFragmentViewPager shFragmentViewPager = new SHFragmentViewPager(getActivity().getSupportFragmentManager());

        SHSubImageFragment shSubImageFragment;
        for(Integer resource : imageResourceList) {
            shSubImageFragment = new SHSubImageFragment();
            shSubImageFragment.setImageResource(resource);
            fragmentList.add(shSubImageFragment);
        }

        shFragmentViewPager.setFragmentList(fragmentList);

        viewPager.setAdapter(shFragmentViewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int currentPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    return;
                }

                if (0 == currentPosition) {
                    viewPager.setCurrentItem(imageResourceList.size() - 2, false);
                }
                else if (imageResourceList.size() - 1 == currentPosition) {
                    viewPager.setCurrentItem(1, false);
                }
            }
        });

        shFragmentViewPager.notifyDataSetChanged();

        viewPager.setCurrentItem(1);

        timer = TimerUtil.createTimerTask(3000, 4000, new Runnable() {
            @Override
            public void run() {
                int index = viewPager.getCurrentItem() + 1;
                viewPager.setCurrentItem( index );
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {

        if(timer != null) {
            timer.cancel();
        }

        super.onDestroyView();
    }
}
