package com.test.rxjava.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.LogUtils;
import com.test.rxjava.BaseFragment;
import com.test.rxjava.databinding.FragmentSample3Binding;
import com.test.rxjava.utils.RxUtil;
import com.test.rxjava.viewmodel.Sample3ViewModel;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * 生命周期与线程控制
 *
 * @author shixingxing
 */
public class Sample3Fragment extends BaseFragment {

    private static final String TAG = Sample3Fragment.class.getSimpleName();

    private FragmentSample3Binding binding;
    private Sample3ViewModel model;

    private ObservableEmitter<Integer> sampleEmitter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisposableObserver observer = RxUtil.io(new RxUtil.RxTask() {
            @Override
            public Object doSth(ObservableEmitter emitter, Object object) {
                for (int i = 1000; i < Integer.MAX_VALUE; i++) {

                    if (emitter.isDisposed()) {
                        break;
                    }
                    Log.e("doSth", Thread.currentThread().getName() + ":--" + String.valueOf(i));

                    if (sampleEmitter != null) {
                        sampleEmitter.onNext(new Random().nextInt());
                    }
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            public void onNext(Object value) {
                Log.e("onNext", String.valueOf(value));
            }
        });
        disposables.add(observer);

        Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {

                    @Override
                    public void subscribe(@Nullable ObservableEmitter<Integer> emitter) throws Throwable {
                        sampleEmitter = emitter;
                    }
                })
                .sample(5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        LogUtils.iTag("sample", integer);
                    }
                });
        disposables.add(disposable);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        model = new ViewModelProvider(this).get(Sample3ViewModel.class);

        binding = FragmentSample3Binding.inflate(inflater);

        return binding.getRoot();
    }


}
