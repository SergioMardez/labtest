package com.sergiom.labtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.ads.AdView
import com.sergiom.labaddlibrary.*
import com.sergiom.labaddlibrary.enumeration.AdType
import com.sergiom.labaddlibrary.enumeration.State
import com.sergiom.labaddlibrary.model.AdDataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataState {

    private lateinit var adView: AdView
    private lateinit var inter: InterstitialInitializer
    private lateinit var rewarded: RewardedVideoInitializer
    private var caves = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize mobile ads here
        AdInitializer(this)

        //Banner - Add the banner programmatically invisible to show it when is load
        adView = AdView(this)
        adView.visibility = View.INVISIBLE
        adView.id = R.id.banner

        //Initialize the banner with the library here. bannerInit.state for the interface delegate
        val bannerInit = BannerInitializer(this, adView, "ca-app-pub-3940256099942544/6300978111")
        bannerInit.state = this

        mainConstraint.addView(bannerInit.banner,
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))


        //Loads interstitial example
        interstitialButton.setOnClickListener {

            //Initialize the Interstitial with the library here. inter.state for the interface delegate
            inter = InterstitialInitializer(this, "ca-app-pub-3940256099942544/8691691433")
            inter.state = this
        }


        //Loads rewarded example
        rewardedButton.setOnClickListener {

            //Initialize the rewarded with the library here. rewarded.state for the interface delegate
            rewarded = RewardedVideoInitializer(this, "ca-app-pub-3940256099942544/5224354917")
            rewarded.state = this
        }

    }

    //On interface change, check the data state and type
    override fun onAdStateChange(dataState: AdDataState?) {
        when {
            dataState?.state == State.LOAD -> when {
                dataState.adType == AdType.BANNER -> {
                    adView.visibility = View.VISIBLE
                }

                dataState.adType == AdType.INTERSTITIAL -> {
                    inter.interstitial.show()
                }

                dataState.adType == AdType.REWARDED -> {
                    rewarded.rewardedVideoAd.show()
                }
            }

            dataState?.state == State.REWARD -> {
                caves += 10
                Toast.makeText(this, "Rewarded! Caves amount: " +
                        caves, Toast.LENGTH_SHORT).show()
            }

            dataState?.state == State.FAILED -> when {
                dataState.adType == AdType.BANNER -> {
                    Toast.makeText(this, "ERROR ON: BANNER", Toast.LENGTH_SHORT).show()
                }

                dataState.adType == AdType.INTERSTITIAL -> {
                    Toast.makeText(this, "ERROR ON: INTERSTITIAL", Toast.LENGTH_SHORT).show()
                }

                dataState.adType == AdType.REWARDED -> {
                    Toast.makeText(this, "ERROR ON: REWARDED", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
