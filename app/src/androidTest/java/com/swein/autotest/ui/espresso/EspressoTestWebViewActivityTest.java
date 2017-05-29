package com.swein.autotest.ui.espresso;

import android.content.Intent;
import android.support.test.espresso.web.assertion.WebViewAssertions;
import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;


@RunWith(AndroidJUnit4.class)
public class EspressoTestWebViewActivityTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(EspressoTestWebViewActivity.class, false);

    @Test
    public void test() throws InterruptedException {

        Intent intent = new Intent();
        intent.putExtra(EspressoTestWebViewActivity.EXTRA_URL, "http://m.baidu.com");
        activityTestRule.launchActivity(intent);

        //find name "word" to find search input
        onWebView().withElement(findElement(Locator.NAME, "word")).perform(DriverAtoms.webKeys("android"));//input "android"

        Thread.sleep(1000);

        //find id "index-bn" to search
        onWebView().withElement(DriverAtoms.findElement(Locator.ID, "index-bn")).perform(webClick());//click search



        //find id "results" to get result div
        onWebView().withElement(DriverAtoms.findElement(Locator.ID, "results"))
                //check is div has "android"
                .check(WebViewAssertions.webMatches(DriverAtoms.getText(), Matchers.containsString("android")));



    }

}