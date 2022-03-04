package com.test.rxjava.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class Sample3Worker : CoroutineWorker {

    private val key: String?

    constructor(appContext: Context, params: WorkerParameters) : super(appContext, params) {
        val data = params.inputData
        key = data.getString("key")
    }


    override suspend fun doWork(): Result {
        Log.i("Thread:", Thread.currentThread().name)
        return Result.success()
    }
}