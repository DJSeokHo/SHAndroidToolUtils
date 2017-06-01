package com.swein.recycleview.random.activity;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.swein.recycleview.random.data.RecyclerViewRandomData;
import com.swein.shandroidtoolutils.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewRandomActivityTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(RecyclerViewRandomActivity.class);

    /**
     * @see #testChangeCheckState()
     * @see #testRefreshDataList()
     */
    @Test
    public void testRecycleView() {

        for(int i = 0; i < 50; i++) {

            testChangeCheckState();

            if(0 == i%5) {
                try {
                    testRefreshDataList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  change item checked state
     */
    public void testChangeCheckState() {
        Espresso.onView(withId(R.id.checkImageButton)).perform(click());
    }

    /**
     * init data list
     *
     */
    public void testRefreshDataList() throws Exception {
        RecyclerViewRandomData.getInstance().initListFromDB();
    }

}