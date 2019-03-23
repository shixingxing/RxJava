package com.test.rxjava.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.rxjava.BaseFragment;
import com.test.rxjava.R;
import com.test.rxjava.databinding.FragmentSample3Binding;
import com.test.rxjava.utils.RxUtil;
import com.test.rxjava.viewmodel.Sample3ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

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
            public Object doSth(Object... object) {
                for (int i = 1000; i < 2000; i++) {

                    Log.e("doSth", String.valueOf(i));

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return "11111";
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

        model = new Sample3ViewModel(getActivity());
        binding.setViewModel(model);

        return binding.getRoot();
    }
}
