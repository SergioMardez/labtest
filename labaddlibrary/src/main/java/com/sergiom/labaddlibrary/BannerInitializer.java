package com.sergiom.labaddlibrary;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.sergiom.labaddlibrary.enumeration.AdType;
import com.sergiom.labaddlibrary.enumeration.State;
import com.sergiom.labaddlibrary.model.AdDataState;

/**
 * This class needs the view of the adBanner to get the correct position
 * */
public class BannerInitializer {

    private AdView view;
    private AdDataState dataState = new AdDataState();
    public DataState state = null;

    public BannerInitializer(Context context, AdView view, String unitID) {
        this.view = view;

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        view.setAdUnitId(unitID);
        view.setAdSize(AdSize.BANNER);
        view.loadAd(adRequest);
        setListeners(view);
    }

    public AdView getBanner() {
        return view;
    }

    private void setListeners(AdView view) {
        view.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                dataState.setState(State.LOAD);
                dataState.setAdType(AdType.BANNER);
                state.onAdStateChange(dataState);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                dataState.setState(State.FAILED);
                dataState.setAdType(AdType.BANNER);
                state.onAdStateChange(dataState);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
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
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

}
