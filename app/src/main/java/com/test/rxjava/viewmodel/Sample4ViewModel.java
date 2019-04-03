package com.test.rxjava.viewmodel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.test.rxjava.service.CMDService;
import com.text.rxjava.ICMDCallBack;
import com.text.rxjava.ICMDInterface;


public class Sample4ViewModel extends MyObservable {

    private ICMDInterface cmdInterface;

    public Sample4ViewModel(Context context) {
        super(context);
    }


    public String getStatusText() {
        return "";
    }

    public void onClickBind(View view) {
        bindService(context, serviceConnection);
    }

    public void onClickUnBind(View view) {
        unBindService(context, serviceConnection);
    }

    /**
     * 绑定服务
     */
    private void bindService(Context context, ServiceConnection serviceConnection) {
        if (context == null || null == serviceConnection) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context, CMDService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解绑服务
     *
     * @param context
     */
    private void unBindService(Context context, ServiceConnection serviceConnection) {
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


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            cmdInterface = ICMDInterface.Stub.asInterface(service);
            try {
                cmdInterface.registerCallback(new CMDCallBack());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                if (null != cmdInterface) {
                    cmdInterface.unRegisterCallback();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            cmdInterface = null;
        }
    };


    class CMDCallBack extends ICMDCallBack.Stub {

        @Override
        public void callback(int value) throws RemoteException {

        }
    }
}
