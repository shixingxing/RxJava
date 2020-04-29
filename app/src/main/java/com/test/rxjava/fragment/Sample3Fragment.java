package com.test.rxjava.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.test.rxjava.BaseFragment;
import com.test.rxjava.R;
import com.test.rxjava.databinding.FragmentSample3Binding;
import com.test.rxjava.utils.RxUtil;
import com.test.rxjava.viewmodel.Sample3ViewModel;

import io.reactivex.ObservableEmitter;

/**
 * 生命周期与线程控制
 *
 * @author shixingxing
 */
public class Sample3Fragment extends BaseFragment {

    private static final String TAG = Sample3Fragment.class.getSimpleName();

    private FragmentSample3Binding binding;
    private Sample3ViewModel model;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxUtil.io(this, new RxUtil.RxTask() {
            @Override
            public Object doSth(ObservableEmitter emitter, Object object) {
                for (int i = 1000; i < 2000; i++) {

                    if (emitter.isDisposed()) {
                        break;
                    }
                    Log.e("doSth", Thread.currentThread().getName() + ":--" + String.valueOf(i));

                    try {
                        Thread.sleep(50);
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sample3, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new ViewModelProvider(this).get(Sample3ViewModel.class);
        binding.setViewModel(model);
    }
}
