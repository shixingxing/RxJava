package com.test.rxjava.service

import android.content.Context
import androidx.work.WorkerParameters
import androidx.work.rxjava3.RxWorker
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Single

class SampleRxWork : RxWorker {
    constructor(appContext: Context, workerParams: WorkerParameters) : super(
        appContext,
        workerParams
    )


    override fun createWork(): Single<Result> {
        return Single.fromObservable(ObservableSource { })
    }
}