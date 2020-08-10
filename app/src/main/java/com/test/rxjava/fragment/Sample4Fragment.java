package com.test.rxjava.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.test.rxjava.R;
import com.test.rxjava.databinding.FragmentSample4Binding;
import com.test.rxjava.viewmodel.Sample4ViewModel;

public class Sample4Fragment extends Fragment {

    private FragmentSample4Binding binding;
    private Sample4ViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSample4Binding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new ViewModelProvider(this).get(Sample4ViewModel.class);

        binding.onClickServerStart.setOnClickListener(v -> model.onClickServerStart(v));
        binding.onClickServerStop.setOnClickListener(v -> model.onClickServerStop(v));

        binding.onClickClientStart.setOnClickListener(v -> model.onClickClientStart(v));
        binding.onClickClientStop.setOnClickListener(v -> model.onClickClientStop(v));
    }
}
