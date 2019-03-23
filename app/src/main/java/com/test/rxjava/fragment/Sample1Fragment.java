package com.test.rxjava.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.rxjava.R;
import com.test.rxjava.adapter.AppInfoAdapter;
import com.test.rxjava.databinding.FragmentSample1Binding;
import com.test.rxjava.viewmodel.Sample1ViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Sample1Fragment extends Fragment {

    private FragmentSample1Binding binding;
    private Sample1ViewModel viewModel;
    private AppInfoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sample1, container, false);

        viewModel = new Sample1ViewModel(getActivity());
        binding.setViewModel(viewModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.recycler.setLayoutManager(layoutManager);

        adapter = new AppInfoAdapter();
        binding.recycler.setAdapter(adapter);
        viewModel.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null) {
            viewModel.getAppInfo();
        }
    }
}
