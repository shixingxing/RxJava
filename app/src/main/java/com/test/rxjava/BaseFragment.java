package com.test.rxjava;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class BaseFragment extends Fragment {

    protected CompositeDisposable disposables;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposables = new CompositeDisposable();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
