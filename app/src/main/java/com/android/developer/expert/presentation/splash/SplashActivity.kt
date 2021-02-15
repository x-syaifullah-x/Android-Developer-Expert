package com.android.developer.expert.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.MainActivity
import com.base.extension.hideSystemUI
import com.base.extension.openActivityAndFinish
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(1000)
            openActivityAndFinish<MainActivity>()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }
}