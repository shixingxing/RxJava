package com.test.rxjava.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.rxjava.R;
import com.test.rxjava.databinding.FragmentSample3Binding;
import com.test.rxjava.interfaces.LifeCycleObserver;
import com.test.rxjava.viewmodel.Sample3ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 生命周期与线程控制
 */
public class Sample3Fragment extends Fragment {

    private static final String TAG = Sample3Fragment.class.getSimpleName();

    private FragmentSample3Binding binding;
    private Sample3ViewModel model;


    Disposable disposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new LifeCycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                super.onCreate(owner);
            }

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                super.onDestroy(owner);
                disposable.dispose();
            }
        });

        disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {

                for (int i = 0; i < 1000; i++) {
                    if (observableEmitter.isDisposed()) {
                        break;
                    }
                    observableEmitter.onNext(i);

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, String.valueOf(integer));
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sample3, container, false);

        model = new Sample3ViewModel(getActivity());
        binding.setViewModel(model);

        return binding.getRoot();
    }
}
