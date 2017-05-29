package com.swein.autotest.ui.espresso;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.web.assertion.WebViewAssertions;
import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.swein.shandroidtoolutils.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;


@RunWith(AndroidJUnit4.class)
public class EspressoTestExampleActivityTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(EspressoTestExampleActivity.class);

    @Test
    public void test() throws InterruptedException {
        //find edittext1, input 2, close keyboard
        Espresso.onView(withId(R.id.editText1)).perform(typeText("1"), closeSoftKeyboard());

        Thread.sleep(1000);

        //find edittext2, input 5, close keyboard
        Espresso.onView(withId(R.id.editText2)).perform(typeText("2"), closeSoftKeyboard());

        Thread.sleep(1000);

        //find calculateButton, click
        Espresso.onView(withId(R.id.calculateButton)).perform(click());

        Thread.sleep(1000);

        //find textViewResult, is result matched
//        Espresso.onView(withId(R.id.textViewResult)).check(matches(withText("result：2")));
        Espresso.onView(withId(R.id.textViewResult)).check(matches(withText("result：3")));
    }





    @Test
    public void testWebView() {
        //find buttonWebView , click, goto EspressoTestWebViewActivity
        Espresso.onView(withText("buttonWebView")).perform(click());
        //find name "word" to find search input
        onWebView().withElement(findElement(Locator.NAME, "word"))
                //input "android"
                .perform(DriverAtoms.webKeys("android"))
                //find id "index-bn" to search
                .withElement(DriverAtoms.findElement(Locator.ID, "index-bn"))
                //click
                .perform(webClick())
                //find id "results" to get result div
                .withElement(DriverAtoms.findElement(Locator.ID, "results"))
                //check is div has "android"
                .check(WebViewAssertions.webMatches(DriverAtoms.getText(), Matchers.containsString("android")));
        //click back and close to EspressoTestWebViewActivity
        Espresso.pressBack();
    }

}