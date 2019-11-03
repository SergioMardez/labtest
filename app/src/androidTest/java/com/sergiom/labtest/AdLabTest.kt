package com.sergiom.labtest

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdLabTest {

    @Before
    fun setUp() {
        IntentsTestRule(MainActivity::class.java, false, false).launchActivity(Intent())
    }

    @After
    fun finish() {
        Intents.release()
    }

    @Test
    fun bannerIsLoaded() {
        Thread.sleep(2000)
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
}