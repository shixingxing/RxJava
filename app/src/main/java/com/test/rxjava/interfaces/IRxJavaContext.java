package com.test.rxjava.interfaces;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface IRxJavaContext {
    List<Disposable> getRxContext();
}
