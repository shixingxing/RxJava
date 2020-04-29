package com.test.rxjava.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.rxjava.R;
import com.test.rxjava.databinding.FragmentSample2Binding;
import com.test.rxjava.viewmodel.Sample2ViewModel;

public class Sample2Fragment extends Fragment {
    private FragmentSample2Binding binding;
    private Sample2ViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sample2, container, false);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.recycler.setLayoutManager(layoutManager);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(Sample2ViewModel.class);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
