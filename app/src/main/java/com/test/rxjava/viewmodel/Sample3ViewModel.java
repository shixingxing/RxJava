package com.test.rxjava.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.test.rxjava.adapter.AppInfoAdapter;
import com.test.rxjava.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class Sample3ViewModel extends MyObservable {
    private AppInfoAdapter adapter;

    public Sample3ViewModel(Context context) {
        super(context);
    }

    public void setAdapter(AppInfoAdapter adapter) {
        this.adapter = adapter;
    }

    public void getAppInfo() {

        List<AppInfo> appInfos = new ArrayList<>();
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfos) {
            appInfos.add(new AppInfo(0, resolveInfo.activityInfo.name, null));
        }

        AppInfo[] infos = new AppInfo[appInfos.size()];
        appInfos.toArray(infos);
        Observable.fromArray(infos).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AppInfo>() {
            @Override
            public void accept(AppInfo o) throws Exception {
                Log.i(getClass().getName(), "onNext");
                adapter.addAppInfo(o);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
