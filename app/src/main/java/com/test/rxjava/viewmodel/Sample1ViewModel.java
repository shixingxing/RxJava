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

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class Sample1ViewModel extends MyObservable {

    private AppInfoAdapter adapter;

    public Sample1ViewModel(Context context) {
        super(context);
    }

    public void setAdapter(AppInfoAdapter adapter) {
        this.adapter = adapter;
    }

    public void getAppInfo() {

        Observable<AppInfo> observable = Observable.create(subscriber -> {
            List<AppInfo> appInfos = new ArrayList<>();
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);

            for (ResolveInfo resolveInfo : resolveInfos) {
                appInfos.add(new AppInfo(0, resolveInfo.activityInfo.loadLabel(packageManager).toString(), null));

                if (subscriber.isUnsubscribed()) {
                    return;
                }
                subscriber.onNext(new AppInfo(0, resolveInfo.activityInfo.name, null));
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        });

        observable.toSortedList().subscribe(appInfos -> {
            Log.i(getClass().getName(), "onNext");
            adapter.setAppInfoList(appInfos);
            adapter.notifyDataSetChanged();
        }, throwable -> {
        }, () -> {
            Log.i(getClass().getName(), "onCompleted");
        });
    }
}
