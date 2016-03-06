package com.test.rxjava.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

public class MyObservable extends BaseObservable implements ViewModel {

    Context context;

    public MyObservable(Context context) {
        this.context = context;
    }

    @Override
    public void destroy() {
        context = null;
    }
}
