package com.test.rxjava.viewmodel;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.test.rxjava.service.MinaClientService;
import com.test.rxjava.service.MinaServerService;


public class Sample4ViewModel extends AndroidViewModel {

    MinaServerService.ServerBinder serverBinder;
    MinaClientService.ClientBinder clientBinder;

    public Sample4ViewModel(@NonNull Application application) {
        super(application);
        bindServerService(getApplication(), serverConnection);
        bindClientService(getApplication(), clientConnection);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        unBindServerService(getApplication(), serverConnection);
        unBindClientService(getApplication(), clientConnection);
    }

    public void onClickServerStart(View view) {
        if (serverBinder != null) {
            serverBinder.start();
        }
    }

    public void onClickServerStop(View view) {
        if (serverBinder != null) {
            serverBinder.stop();
        }
    }

    public void onClickClientStart(View view) {
        if (clientBinder != null) {
            clientBinder.start();
        }
    }

    public void onClickClientStop(View view) {
        if (clientBinder != null) {
            clientBinder.stop();
        }
    }

    /**
     * 服务端服务
     */
    private void bindServerService(Context context, ServiceConnection serviceConnection) {
        if (context == null || null == serviceConnection) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context, MinaServerService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解绑服务
     *
     * @param context
     */
    private void unBindServerService(Context context, ServiceConnection serviceConnection) {
        if (context == null || null == serviceConnection) {
            return;
        }
        try {
            // 当需要多次调用doSomething()方法的时候，如果直接bindService是会报错的
            context.unbindService(serviceConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ServiceConnection serverConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serverBinder = (MinaServerService.ServerBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serverBinder = null;
        }
    };


    /**
     * 客户端服务
     */
    private void bindClientService(Context context, ServiceConnection serviceConnection) {
        if (context == null || null == serviceConnection) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context, MinaClientService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解绑服务
     *
     * @param context
     */
    private void unBindClientService(Context context, ServiceConnection serviceConnection) {
        if (context == null || null == serviceConnection) {
            return;
        }
        try {
            // 当需要多次调用doSomething()方法的时候，如果直接bindService是会报错的
            context.unbindService(serviceConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ServiceConnection clientConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            clientBinder = (MinaClientService.ClientBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            clientBinder = null;
        }
    };

}
