package com.test.rxjava.interfaces;

import android.util.Log;

import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public class LifeCycleObserver implements GenericLifecycleObserver {
    private static String TAG = LifeCycleObserver.class.getSimpleName();

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        Log.d(TAG, "onStateChanged"+event.name());
    }
}
