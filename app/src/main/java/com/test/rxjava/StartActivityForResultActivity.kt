package com.test.rxjava

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

class StartActivityForResultActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val input = intent?.getStringExtra("input") ?: ""

        Log.i("input", input)

        Handler(Looper.myLooper()!!).postDelayed({

            setResult(RESULT_OK, Intent().apply {
                putExtra("output", "def")
            })
            finish()
        }, 1000)
    }
}