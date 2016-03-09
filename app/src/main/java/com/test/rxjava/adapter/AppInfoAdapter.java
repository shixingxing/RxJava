package com.test.rxjava.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.test.rxjava.R;
import com.test.rxjava.databinding.LayoutAppinfoItemBinding;
import com.test.rxjava.model.AppInfo;
import com.test.rxjava.viewmodel.ItemAppInfoViewModel;

import java.util.ArrayList;
import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {

    private LayoutInflater inflate;
    private List<AppInfo> appInfoList;

    public void setAppInfoList(List<AppInfo> appInfoList) {
        this.appInfoList = appInfoList;
    }

    public void addAppInfo(AppInfo appInfo) {
        if (appInfo == null) {
            return;
        }
        if (appInfoList == null) {
            appInfoList = new ArrayList<>();
        }

        appInfoList.add(appInfo);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflate == null)
            inflate = LayoutInflater.from(parent.getContext());
        LayoutAppinfoItemBinding binding = DataBindingUtil.inflate(inflate, R.layout.layout_appinfo_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindingAppInfo(appInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        if (appInfoList == null) {
            return 0;
        } else {
            return appInfoList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final LayoutAppinfoItemBinding binding;

        public ViewHolder(LayoutAppinfoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindingAppInfo(AppInfo appInfo) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemAppInfoViewModel(itemView.getContext(), appInfo));
            } else {
                binding.getViewModel().setAppInfo(appInfo);
            }
        }

    }
}
