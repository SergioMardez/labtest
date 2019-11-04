package com.sergiom.labtest

import android.content.Intent
import android.net.wifi.WifiManager
import android.view.WindowManager
import androidx.core.content.getSystemService
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdLabTest {
    @get:Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        mActivityRule.launchActivity(Intent())
    }

    @Test
    fun bannerIsLoaded() {
        Thread.sleep(3000)
        //Check view is displayed
        onView(withId(R.id.banner)).check(matches(isDisplayed()))
    }

    @Test
    fun interstitialIsShown() {
        // Click show interstitial button
        val showInterstitialButton = onView(withId(R.id.interstitialButton))
        showInterstitialButton.check(matches(isDisplayed()))
        showInterstitialButton.perform(click())

        Thread.sleep(12000)

        // Check interstitial button (close by back button)
        onView(allOf(withContentDescription("Interstitial close button"), isDisplayed()))
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun rewardedIsShown() {
        // Click show interstitial button
        val showInterstitialButton = onView(withId(R.id.rewardedButton))
        showInterstitialButton.check(matches(isDisplayed()))
        showInterstitialButton.perform(click())

        Thread.sleep(12000)

        // Check rewarded button (close by back button)
        onView(allOf(withContentDescription("Rewarded close button"), isDisplayed()))
        onView(isRoot()).perform(pressBack())
    }

    /*
    * On Failure Loading (NO INTERNET)
    * */
    @Test
    fun noInternetShowsBannerToast() {
        mActivityRule.activity.getSystemService<WifiManager>()?.isWifiEnabled = false

        onView(withText("ERROR ON: BANNER")).inRoot(ToastMatcher()).check(matches(isDisplayed()))

        mActivityRule.activity.getSystemService<WifiManager>()?.isWifiEnabled = true
        Thread.sleep(5000) //Wait for wifi be enabled again
    }

    @Test
    fun noInternetShowsInterstitialToast() {
        Thread.sleep(3000) //Wait for the banner to load
        mActivityRule.activity.getSystemService<WifiManager>()?.isWifiEnabled = false

        onView(withId(R.id.interstitialButton)).perform(click())

        onView(withText("ERROR ON: INTERSTITIAL")).inRoot(ToastMatcher()).check(matches(isDisplayed()))

        mActivityRule.activity.getSystemService<WifiManager>()?.isWifiEnabled = true
        Thread.sleep(5000)
    }

    @Test
    fun noInternetShowsRewardedToast() {
        Thread.sleep(3000)
        mActivityRule.activity.getSystemService<WifiManager>()?.isWifiEnabled = false

        onView(withId(R.id.rewardedButton)).perform(click())

        onView(withText("ERROR ON: REWARDED")).inRoot(ToastMatcher()).check(matches(isDisplayed()))

        mActivityRule.activity.getSystemService<WifiManager>()?.isWifiEnabled = true
        Thread.sleep(5000)
    }


    //This will check if a toast is displayed
    class ToastMatcher : TypeSafeMatcher<Root>() {
        override fun describeTo(description: Description) {
            description.appendText("is toast")
        }

        override fun matchesSafely(root: Root): Boolean {
            val type = root.windowLayoutParams.get().type
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                val windowToken = root.decorView.windowToken
                val appToken = root.decorView.applicationWindowToken
                if (windowToken == appToken) {
                    //means this window isn't contained by any other windows.
                    return true
                }
            }
            return false
        }
    }
}