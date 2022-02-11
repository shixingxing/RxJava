package com.test.rxjava;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.CameraXConfig;
import androidx.multidex.MultiDexApplication;

public class MainApplication extends MultiDexApplication implements CameraXConfig.Provider {

    /**
     * Returns the configuration to use for initializing an instance of CameraX.
     */
    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {
        return CameraXConfig.Builder.fromConfig(Camera2Config.defaultConfig())
                .setMinimumLoggingLevel(Log.ERROR).build();
    }
}
