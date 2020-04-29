package com.test.rxjava.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.test.rxjava.utils.MinaServer;

import java.lang.ref.SoftReference;

/**
 * 服务器
 *
 * @author shixingxing
 */
public class MinaServerService extends Service {

    private static final String TAG = MinaServerService.class.getSimpleName();

    private ServerBinder binder = new ServerBinder(this);

    private MinaServer server;

    @Override
    public void onCreate() {
        super.onCreate();
        server = new MinaServer();
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

    public static class ServerBinder extends Binder {


        private SoftReference<MinaServerService> softReference;

        ServerBinder(MinaServerService serverService) {
            softReference = new SoftReference<>(serverService);
        }

        public void start() {
            Log.i(TAG, "start");
            if (softReference != null && softReference.get() != null) {
                softReference.get().server.start();
            }
        }

        public void stop() {
            Log.i(TAG, "stop");
            if (softReference != null && softReference.get() != null) {
                softReference.get().server.stop();
            }
        }

    }
}
