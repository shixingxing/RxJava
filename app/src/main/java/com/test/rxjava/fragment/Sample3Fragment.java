package com.test.rxjava.fragment;

import android.os.Bundle;
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

public class Sample3Fragment extends Fragment {

    private FragmentSample3Binding binding;
    private Sample3ViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new LifeCycleObserver());
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
