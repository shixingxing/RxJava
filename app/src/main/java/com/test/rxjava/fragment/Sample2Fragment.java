package com.test.rxjava.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.rxjava.R;
import com.test.rxjava.adapter.AppInfoAdapter;
import com.test.rxjava.databinding.FragmentSample2Binding;
import com.test.rxjava.viewmodel.Sample2ViewModel;

public class Sample2Fragment extends Fragment {
    private FragmentSample2Binding binding;
    private Sample2ViewModel viewModel;
    private AppInfoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sample2, container, false);

        viewModel = new Sample2ViewModel(getActivity());
        binding.setViewModel(viewModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recycler.setLayoutManager(layoutManager);

        adapter = new AppInfoAdapter();
        binding.recycler.setAdapter(adapter);
        viewModel.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null)
            viewModel.getAppInfo();
    }
}
