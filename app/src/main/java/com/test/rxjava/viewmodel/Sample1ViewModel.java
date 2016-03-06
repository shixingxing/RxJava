package com.test.rxjava.viewmodel;

import android.content.Context;

import com.test.rxjava.adapter.AppInfoAdapter;

public class Sample1ViewModel extends MyObservable {

    private AppInfoAdapter adapter;

    public Sample1ViewModel(Context context) {
        super(context);
    }

    public void setAdapter(AppInfoAdapter adapter) {
        this.adapter = adapter;
    }
}
