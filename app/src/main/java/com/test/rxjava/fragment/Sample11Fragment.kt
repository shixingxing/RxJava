package com.test.rxjava.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.test.rxjava.BaseFragment

class Sample11Fragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        /**
         * Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
         */
        override fun handleOnBackPressed() {
            Log.i("OnBackPressedDispatcher", "handleOnBackPressed")
        }

    }
}