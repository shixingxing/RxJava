package com.test.rxjava.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import com.andrognito.patternlockview.utils.ResourceUtils
import com.test.rxjava.BaseFragment
import com.test.rxjava.R
import kotlinx.android.synthetic.main.fragment_sample9.*

public class Sample9Fragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample9, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patter_lock_view.setDotCount(3)
        patter_lock_view.setDotNormalSize(ResourceUtils.getDimensionInPx(requireActivity(), R.dimen.pattern_lock_dot_size).toInt())
        patter_lock_view.setDotSelectedSize(ResourceUtils.getDimensionInPx(requireActivity(), R.dimen.pattern_lock_dot_selected_size).toInt())
        patter_lock_view.setPathWidth(ResourceUtils.getDimensionInPx(requireActivity(), R.dimen.pattern_lock_path_width).toInt())
        patter_lock_view.setAspectRatioEnabled(true)
        patter_lock_view.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS)
        patter_lock_view.setViewMode(PatternLockView.PatternViewMode.CORRECT)
        patter_lock_view.setDotAnimationDuration(150)
        patter_lock_view.setPathEndAnimationDuration(100)
        patter_lock_view.setCorrectStateColor(ResourceUtils.getColor(requireActivity(), R.color.white))
        patter_lock_view.setInStealthMode(false)
        patter_lock_view.setTactileFeedbackEnabled(false)
        patter_lock_view.setInputEnabled(true)
        patter_lock_view.addPatternLockListener(mPatternLockViewListener)
    }


    private val mPatternLockViewListener: PatternLockViewListener = object : PatternLockViewListener {
        override fun onStarted() {
            Log.d(javaClass.name, "Pattern drawing started")
        }

        override fun onProgress(progressPattern: List<PatternLockView.Dot>) {
            Log.d(javaClass.name, "Pattern progress: " +
                    PatternLockUtils.patternToString(patter_lock_view, progressPattern))
        }

        override fun onComplete(pattern: List<PatternLockView.Dot>) {
            Log.d(javaClass.name, "Pattern complete: " +
                    PatternLockUtils.patternToString(patter_lock_view, pattern))
        }

        override fun onCleared() {
            Log.d(javaClass.name, "Pattern has been cleared")
        }
    }
}