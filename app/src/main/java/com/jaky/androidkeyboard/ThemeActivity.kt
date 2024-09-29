package com.jaky.androidkeyboard

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class ThemeActivity : AppCompatActivity(), View.OnClickListener {
    private var mInterstitialAd: InterstitialAd? = null
    private var adRequest: AdRequest? = null

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var counter: Int? = null
    private val TAG="######DEBUG###########"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        prepareInter()

        val themeButton1 = findViewById<ImageButton>(R.id.theme1_imageButton)
        val themeButton2 = findViewById<ImageButton>(R.id.theme2_imageButton)
        val themeButton3 = findViewById<ImageButton>(R.id.theme3_imageButton)
        val themeButton4 = findViewById<ImageButton>(R.id.theme4_imageButton)
        val themeButton5 = findViewById<ImageButton>(R.id.theme5_imageButton)
        val themeButton6 = findViewById<ImageButton>(R.id.theme6_imageButton)
        val themeButton7 = findViewById<ImageButton>(R.id.theme7_imageButton)
        val themeButton8 = findViewById<ImageButton>(R.id.theme8_imageButton)
        val themeButton9 = findViewById<ImageButton>(R.id.theme9_imageButton)
        val themeButton10 = findViewById<ImageButton>(R.id.theme10_imageButton)

        themeButton1.setOnClickListener(this)
        themeButton2.setOnClickListener(this)
        themeButton3.setOnClickListener(this)
        themeButton4.setOnClickListener(this)
        themeButton5.setOnClickListener(this)
        themeButton6.setOnClickListener(this)
        themeButton7.setOnClickListener(this)
        themeButton8.setOnClickListener(this)
        themeButton9.setOnClickListener(this)
        themeButton10.setOnClickListener(this)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        if (actionBar != null) {
            actionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val adView = this.findViewById<AdView>(R.id.adView)
        adRequest = AdRequest.Builder()
            .build()
        adView.loadAd(adRequest!!)

        // Display the full screen Ad after third visit.
        counter = sharedPreferences?.getInt(AD_COUNT, 0)
        editor = sharedPreferences?.edit()

        if (2 == counter) {
            if(mInterstitialAd!=null){
                mInterstitialAd?.show(this)
            }
        } else {
            editor?.putInt(AD_COUNT, sharedPreferences?.getInt(AD_COUNT, 0)!! + 1)?.apply()
        }

    }

    private fun prepareInter() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "getString(R.string.inter_id)",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdClicked() {
                                Log.d(TAG, "Ad was clicked.")
                            }

                            override fun onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                Log.d(TAG, "Ad dismissed fullscreen content.")
                                mInterstitialAd = null
                                finish() // Tutup activity setelah iklan di-dismiss
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                Log.e(TAG, "Ad failed to show fullscreen content.")
                                mInterstitialAd = null
                                finish() // Jika iklan gagal ditampilkan, tetap tutup activity
                            }

                            override fun onAdImpression() {
                                Log.d(TAG, "Ad recorded an impression.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                Log.d(TAG, "Ad showed fullscreen content.")
                            }
                        }
                }
            }
        )
    }

    override fun onClick(view: View) {
        val editor = sharedPreferences!!.edit()

        when (view.id) {
            R.id.theme1_imageButton -> editor.putInt(THEME_KEY, 0).apply()
            R.id.theme2_imageButton -> editor.putInt(THEME_KEY, 1).apply()
            R.id.theme3_imageButton -> editor.putInt(THEME_KEY, 2).apply()
            R.id.theme4_imageButton -> editor.putInt(THEME_KEY, 3).apply()
            R.id.theme5_imageButton -> editor.putInt(THEME_KEY, 4).apply()
            R.id.theme6_imageButton -> editor.putInt(THEME_KEY, 5).apply()
            R.id.theme7_imageButton -> editor.putInt(THEME_KEY, 6).apply()
            R.id.theme8_imageButton -> editor.putInt(THEME_KEY, 7).apply()
            R.id.theme9_imageButton -> editor.putInt(THEME_KEY, 8).apply()
            R.id.theme10_imageButton -> editor.putInt(THEME_KEY, 9).apply()
            else -> {}
        }
        Toast.makeText(this, "Theme is selected.", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (2 == counter) {
            mInterstitialAd?.show(this);

            //Reset the counter

            editor!!.putInt(AD_COUNT, sharedPreferences!!.getInt(AD_COUNT, 0)).apply()
        }
        super.onBackPressed()
    }

    companion object {
        const val THEME_KEY: String = "theme_key"
        const val AD_COUNT: String = "ad_count"
    }
}