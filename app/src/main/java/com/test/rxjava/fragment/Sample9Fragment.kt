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
import com.test.rxjava.databinding.FragmentSample9Binding

public class Sample9Fragment : BaseFragment() {

    private lateinit var mBinding: FragmentSample9Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentSample9Binding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.patterLockView.setDotCount(3)
        mBinding.patterLockView.setDotNormalSize(ResourceUtils.getDimensionInPx(requireActivity(), R.dimen.pattern_lock_dot_size).toInt())
        mBinding.patterLockView.setDotSelectedSize(ResourceUtils.getDimensionInPx(requireActivity(), R.dimen.pattern_lock_dot_selected_size).toInt())
        mBinding.patterLockView.setPathWidth(ResourceUtils.getDimensionInPx(requireActivity(), R.dimen.pattern_lock_path_width).toInt())
        mBinding.patterLockView.setAspectRatioEnabled(true)
        mBinding.patterLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS)
        mBinding.patterLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT)
        mBinding.patterLockView.setDotAnimationDuration(150)
        mBinding.patterLockView.setPathEndAnimationDuration(100)
        mBinding.patterLockView.setCorrectStateColor(ResourceUtils.getColor(requireActivity(), R.color.white))
        mBinding.patterLockView.setInStealthMode(false)
        mBinding.patterLockView.setTactileFeedbackEnabled(false)
        mBinding.patterLockView.setInputEnabled(true)
        mBinding.patterLockView.addPatternLockListener(mPatternLockViewListener)
    }


    private val mPatternLockViewListener: PatternLockViewListener = object : PatternLockViewListener {
        override fun onStarted() {
            Log.d(javaClass.name, "Pattern drawing started")
        }

        override fun onProgress(progressPattern: List<PatternLockView.Dot>) {
            Log.d(javaClass.name, "Pattern progress: " +
                    PatternLockUtils.patternToString(mBinding.patterLockView, progressPattern))
        }

        override fun onComplete(pattern: List<PatternLockView.Dot>) {
            Log.d(javaClass.name, "Pattern complete: " +
                    PatternLockUtils.patternToString(mBinding.patterLockView, pattern))
        }

        override fun onCleared() {
            Log.d(javaClass.name, "Pattern has been cleared")
        }
    }
}