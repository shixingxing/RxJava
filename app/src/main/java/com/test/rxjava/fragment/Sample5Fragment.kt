package com.test.rxjava.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.test.rxjava.R
import com.test.rxjava.service.Sample3Worker
import com.test.rxjava.service.SampleWork
import java.util.concurrent.TimeUnit

/**
 * work
 */
class Sample5Fragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = Data.Builder().putString("key","token").build()

        val workManager = context?.let { WorkManager.getInstance(it) }
        workManager?.enqueue(OneTimeWorkRequest.Builder(Sample3Worker::class.java).setInputData(data).build())
//        workManager?.enqueue(PeriodicWorkRequest.Builder(SampleWork::class.java, 20, TimeUnit.MINUTES).build())
    }
}