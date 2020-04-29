package com.test.rxjava.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * 服务器
 *
 * @author shixingxing
 */
public class MinaServerService extends Service {

    private static final String TAG = MinaServerService.class.getSimpleName();

    private ServerBinder binder = new ServerBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public static class ServerBinder extends Binder {

        public void start() {
            Log.i(TAG, "start");
        }

        public void stop() {
            Log.i(TAG, "stop");
        }

    }
}
