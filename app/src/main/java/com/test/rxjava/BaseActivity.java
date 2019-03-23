package com.test.rxjava;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(observer);
    }

    private DefaultLifecycleObserver observer = new DefaultLifecycleObserver() {

        /**
         * Notifies that {@code ON_CREATE} event occurred.
         * <p>
         * This method will be called after the {@link LifecycleOwner}'s {@code onCreate}
         * method returns.
         *
         * @param owner the component, whose state was changed
         */
        @Override
        public void onCreate(@NonNull LifecycleOwner owner) {
            Log.d(TAG, "onCreate");
        }

        /**
         * Notifies that {@code ON_START} event occurred.
         * <p>
         * This method will be called after the {@link LifecycleOwner}'s {@code onStart} method returns.
         *
         * @param owner the component, whose state was changed
         */
        @Override
        public void onStart(@NonNull LifecycleOwner owner) {
            Log.d(TAG, "onStart");
        }

        /**
         * Notifies that {@code ON_RESUME} event occurred.
         * <p>
         * This method will be called after the {@link LifecycleOwner}'s {@code onResume}
         * method returns.
         *
         * @param owner the component, whose state was changed
         */
        @Override
        public void onResume(@NonNull LifecycleOwner owner) {
            Log.d(TAG, "onResume");
        }

        /**
         * Notifies that {@code ON_PAUSE} event occurred.
         * <p>
         * This method will be called before the {@link LifecycleOwner}'s {@code onPause} method
         * is called.
         *
         * @param owner the component, whose state was changed
         */
        @Override
        public void onPause(@NonNull LifecycleOwner owner) {
            Log.d(TAG, "onPause");
        }

        /**
         * Notifies that {@code ON_STOP} event occurred.
         * <p>
         * This method will be called before the {@link LifecycleOwner}'s {@code onStop} method
         * is called.
         *
         * @param owner the component, whose state was changed
         */
        @Override
        public void onStop(@NonNull LifecycleOwner owner) {
            Log.d(TAG, "onStop");
        }

        /**
         * Notifies that {@code ON_DESTROY} event occurred.
         * <p>
         * This method will be called before the {@link LifecycleOwner}'s {@code onDestroy} method
         * is called.
         *
         * @param owner the component, whose state was changed
         */
        @Override
        public void onDestroy(@NonNull LifecycleOwner owner) {
            Log.d(TAG, "onDestroy");
        }

    };
}
