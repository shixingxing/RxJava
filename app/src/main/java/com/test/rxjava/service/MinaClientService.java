package com.test.rxjava.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * 客户端
 *
 * @author shixingxing
 */
public class MinaClientService extends Service {
    private static final String TAG = MinaClientService.class.getSimpleName();

    private ClientBinder binder = new ClientBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public static class ClientBinder extends Binder {

        public void start() {
            Log.i(TAG, "start");
        }

        public void stop() {
            Log.i(TAG, "stop");
        }

    }

}
