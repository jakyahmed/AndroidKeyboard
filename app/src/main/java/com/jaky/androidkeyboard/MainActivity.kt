package com.jaky.androidkeyboard

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.jaky.androidkeyboard.android.ImePreferences

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enableSetting = findViewById<LinearLayout>(R.id.layout_EnableSetting)
        val addKeyboards = findViewById<LinearLayout>(R.id.layout_AddLanguages)
        val chooseInputMethod = findViewById<LinearLayout>(R.id.layout_ChooseInput)
        val chooseTheme = findViewById<LinearLayout>(R.id.layout_ChooseTheme)
        val manageDictionaries = findViewById<LinearLayout>(R.id.layout_ManageDictionary)
        val about = findViewById<LinearLayout>(R.id.layout_about)

        enableSetting.setOnClickListener(this)
        addKeyboards.setOnClickListener(this)
        chooseInputMethod.setOnClickListener(this)
        chooseTheme.setOnClickListener(this)
        manageDictionaries.setOnClickListener(this)
        about.setOnClickListener(this)

        val adView = this.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder()
            .build()
        adView.loadAd(adRequest)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.layout_EnableSetting -> startActivityForResult(
                Intent(Settings.ACTION_INPUT_METHOD_SETTINGS), 0
            )

            R.id.layout_AddLanguages -> lunchPreferenceActivity()
            R.id.layout_ChooseInput -> if (isInputEnabled) {
                (applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                    .showInputMethodPicker()
            } else {
                Toast.makeText(this, "Please enable keyboard first.", Toast.LENGTH_SHORT).show()
            }

            R.id.layout_ChooseTheme -> startActivity(Intent(this, ThemeActivity::class.java))
            R.id.layout_ManageDictionary -> startActivity(
                Intent(
                    this,
                    DictionaryActivity::class.java
                )
            )

            R.id.layout_about -> startActivity(Intent(this, AboutActivity::class.java))
            else -> {}
        }
    }


    val isInputEnabled: Boolean
        get() {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            val mInputMethodProperties = imm.enabledInputMethodList

            val N = mInputMethodProperties.size
            var isInputEnabled = false

            for (i in 0 until N) {
                val imi = mInputMethodProperties[i]
                Log.d("INPUT ID", imi.id.toString())
                if (imi.id.contains(packageName)) {
                    isInputEnabled = true
                }
            }

            return if (isInputEnabled) {
                true
            } else {
                false
            }
        }

    fun lunchPreferenceActivity() {
        if (isInputEnabled) {
            val intent = Intent(this, ImePreferences::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please enable keyboard first.", Toast.LENGTH_SHORT).show()
        }
    }
}