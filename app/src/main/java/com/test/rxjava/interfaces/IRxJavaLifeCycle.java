package com.test.rxjava.interfaces;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface IRxJavaLifeCycle {

    /**
     * 获取lifecycle容器
     *
     * @return
     */
    List<Disposable> getRxLifeCycle();
}
