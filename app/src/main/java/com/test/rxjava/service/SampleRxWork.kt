package com.test.rxjava.service

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe

class SampleRxWork :RxWorker {
    constructor(appContext: Context, workerParams: WorkerParameters) : super(appContext, workerParams)


    override fun createWork(): Single<Result> {
        return Single.fromObservable(ObservableSource {  })
    }
}