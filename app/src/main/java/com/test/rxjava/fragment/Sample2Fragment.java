package com.test.rxjava.fragment;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.test.rxjava.databinding.FragmentSample2Binding;
import com.test.rxjava.viewmodel.Sample2ViewModel;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * opengl
 *
 * @author shixingxing
 */
public class Sample2Fragment extends Fragment {
    private FragmentSample2Binding binding;
    private Sample2ViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSample2Binding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(Sample2ViewModel.class);

        binding.glSurfaceView.setEGLContextClientVersion(2);
        binding.glSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {

            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {

            }

            @Override
            public void onDrawFrame(GL10 gl) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.glSurfaceView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.glSurfaceView.onPause();
    }
}
