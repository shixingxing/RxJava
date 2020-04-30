package com.test.rxjava.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.test.rxjava.utils.MinaClient;

import java.lang.ref.SoftReference;

/**
 * 客户端
 *
 * @author shixingxing
 */
public class MinaClientService extends Service {
    private static final String TAG = MinaClientService.class.getSimpleName();

    private ClientBinder binder = new ClientBinder(this);

    private MinaClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new MinaClient();
    }

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

        private SoftReference<MinaClientService> softReference;

        ClientBinder(MinaClientService clientService) {
            softReference = new SoftReference<>(clientService);
        }

        public void start() {
            Log.i(TAG, "start");
            if (softReference != null && softReference.get() != null) {
                softReference.get().client.start();
            }
        }

        public void stop() {
            Log.i(TAG, "stop");
            if (softReference != null && softReference.get() != null) {
                softReference.get().client.stop();
            }
        }

    }

}
