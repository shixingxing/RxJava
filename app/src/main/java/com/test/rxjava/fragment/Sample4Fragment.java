package com.test.rxjava.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.rxjava.R;
import com.test.rxjava.databinding.FragmentSample4Binding;
import com.test.rxjava.viewmodel.Sample4ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class Sample4Fragment extends Fragment {

    private FragmentSample4Binding binding;
    private Sample4ViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sample4, container, false);

        model = new Sample4ViewModel(getContext());
        binding.setViewModel(model);

        return binding.getRoot();
    }
}
