package com.sergiom.labaddlibrary;

import android.content.Context;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.sergiom.labaddlibrary.enumeration.AdType;
import com.sergiom.labaddlibrary.enumeration.State;
import com.sergiom.labaddlibrary.model.AdDataState;

public class InterstitialInitializer {

    private InterstitialAd mInterstitialAd;
    private AdDataState dataState = new AdDataState();
    public DataState state = null;

    public InterstitialInitializer(Context context, String unitID) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(unitID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        setListeners();
    }

    public InterstitialAd getInterstitial() {
        return mInterstitialAd;
    }

    private void setListeners() {
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                //mInterstitialAd.show();
                dataState.setState(State.LOAD);
                dataState.setAdType(AdType.INTERSTITIAL);
                state.onAdStateChange(dataState);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                dataState.setState(State.FAILED);
                dataState.setAdType(AdType.INTERSTITIAL);
                state.onAdStateChange(dataState);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
    }

}
