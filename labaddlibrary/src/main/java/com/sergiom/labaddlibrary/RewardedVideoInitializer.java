package com.sergiom.labaddlibrary;

import android.content.Context;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.sergiom.labaddlibrary.enumeration.AdType;
import com.sergiom.labaddlibrary.enumeration.State;
import com.sergiom.labaddlibrary.model.AdDataState;

public class RewardedVideoInitializer implements RewardedVideoAdListener {

    private RewardedVideoAd mRewardedVideoAd;
    private AdDataState dataState = new AdDataState();
    public DataState state = null;

    public RewardedVideoInitializer(Context context, String unitID) {
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd(unitID);
    }

    private void loadRewardedVideoAd(String unitID) {
        mRewardedVideoAd.loadAd(unitID,
                new AdRequest.Builder().build());
    }

    public RewardedVideoAd getRewardedVideoAd() {
        return mRewardedVideoAd;
    }

    @Override
    public void onRewarded(RewardItem reward) {
        // Reward the user here.
        dataState.setState(State.REWARD);
        dataState.setAdType(AdType.REWARDED);
        state.onAdStateChange(dataState);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        //Toast.makeText(context, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //Toast.makeText(context, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        dataState.setState(State.FAILED);
        dataState.setAdType(AdType.REWARDED);
        state.onAdStateChange(dataState);
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        dataState.setState(State.LOAD);
        dataState.setAdType(AdType.REWARDED);
        state.onAdStateChange(dataState);
    }

    @Override
    public void onRewardedVideoAdOpened() {
        //Toast.makeText(context, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        //Toast.makeText(context, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        //Toast.makeText(context, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
    }
}
