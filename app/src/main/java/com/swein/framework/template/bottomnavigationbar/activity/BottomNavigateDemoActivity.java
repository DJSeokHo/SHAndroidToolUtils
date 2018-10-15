package com.swein.framework.template.bottomnavigationbar.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.swein.framework.template.bottomnavigationbar.adapter.BottomNavigateViewPagerAdapter;
import com.swein.framework.template.bottomnavigationbar.fragments.BottomEventFragment;
import com.swein.framework.template.bottomnavigationbar.fragments.BottomFriendFragment;
import com.swein.framework.template.bottomnavigationbar.fragments.BottomHomeFragment;
import com.swein.framework.template.bottomnavigationbar.fragments.BottomProfileFragment;
import com.swein.framework.tools.util.views.bottomnavigationview.BottomNavigationViewUtil;
import com.swein.shandroidtoolutils.R;

public class BottomNavigateDemoActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigate_demo);

        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        /* if index > 3 will disable scroll, so disable shift mode */
        BottomNavigationViewUtil.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.itemFriends:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.itemHome:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.itemEvent:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.itemProfile:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        BottomNavigateViewPagerAdapter bottomNavigateViewPagerAdapter = new BottomNavigateViewPagerAdapter(getSupportFragmentManager());

        bottomNavigateViewPagerAdapter.addFragment(new BottomFriendFragment());
        bottomNavigateViewPagerAdapter.addFragment(new BottomHomeFragment());
        bottomNavigateViewPagerAdapter.addFragment(new BottomEventFragment());
        bottomNavigateViewPagerAdapter.addFragment(new BottomProfileFragment());

        viewPager.setAdapter(bottomNavigateViewPagerAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void disableViewPagerScroll(ViewPager viewPager) {

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }
}
