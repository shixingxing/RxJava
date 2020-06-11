package com.test.rxjava.service

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * 工作 worker
 */
class SampleWork : Worker {
    private val key: String?;

    constructor(context: Context, workerParams: WorkerParameters) : super(context, workerParams) {
        val data = workerParams.inputData
        key = data.getString("key")
    }


    override fun doWork(): Result {
        Log.i(SampleWork::class.simpleName, Thread.currentThread().name + "I am working:" + key)
        return Result.success()
    }
}