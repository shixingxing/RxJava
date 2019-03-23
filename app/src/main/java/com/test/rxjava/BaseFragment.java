package com.test.rxjava;

import android.os.Bundle;

import com.test.rxjava.interfaces.IRxJavaLifeCycle;
import com.test.rxjava.interfaces.LifeCycleObserver;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.disposables.Disposable;

public class BaseFragment extends Fragment implements IRxJavaLifeCycle {

    protected List<Disposable> disposables;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposables = new ArrayList<>();
        getLifecycle().addObserver(new LifeCycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                super.onCreate(owner);
            }

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                super.onDestroy(owner);
                for (Disposable disposable : disposables) {
                    disposable.dispose();
                }

                disposables.clear();
            }
        });
    }

    @Override
    public List<Disposable> getRxLifeCycle() {
        return disposables;
    }
}
