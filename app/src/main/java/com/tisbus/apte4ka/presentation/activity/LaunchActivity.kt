package com.tisbus.apte4ka.presentation.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenDelay()
    }

    //hide status bar
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
    //Delay launch activity
    private fun splashScreenDelay() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                //delete activity to backstack
                finish()
            },
            2000
        )
    }
}