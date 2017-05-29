package com.swein.recycleview.list.activity;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.swein.recycleview.list.data.RecyclerViewListData;
import com.swein.shandroidtoolutils.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewListActivityTest{

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(RecyclerViewListActivity.class, false);

    /**
     * @see #testScrollWithIndex : scroll to index
     * @see #testClickItemWithMarchedText : click item
     * @see #testLoadMoreData : load more data
     */
    @Test
    public void test() {

        //scroll to 25'th item
        testScrollWithIndex(25);

        //click item which has text "25" on it
        testClickItemWithMarchedText("25");

        //click load more 10 data
        testLoadMoreData();

        //scroll to 25'th item
        testScrollWithIndex(40);

        //click item that has text "5" on it
        testClickItemWithMarchedText("5");

        //load more 10 data
        testLoadMoreData();

        //scroll to 50'th item
        testScrollWithIndex(50);

        //click item that has text "7" on it
        testClickItemWithMarchedText("7");

    }

    public void testLoadMoreData() {
        try {
            Thread.sleep(1000);
            RecyclerViewListData.getInstance().loadList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void testClickItemWithMarchedText(String string) {
        //click item that has text "8" on it
        try {
            Thread.sleep(1000);
            onView(withText(string)).check(matches(isDisplayed())).perform(click());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testScrollWithIndex(int itemIndex) {
        try {
            Thread.sleep(1000);
            onView(withId(R.id.recyclerViewList)).perform(RecyclerViewActions.scrollToPosition(itemIndex));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}