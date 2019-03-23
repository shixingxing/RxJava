package com.test.rxjava.utils;

import com.test.rxjava.interfaces.IRxJavaContext;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {
    private static final String TAG = RxUtil.class.getSimpleName();

    public static void io(IRxJavaContext context, RxTask task) {


        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
                if (observableEmitter.isDisposed()) {
                    return;
                }

                if (task != null) {
                    Object value = task.doSth();
                    if (!observableEmitter.isDisposed()) {
                        observableEmitter.onNext(value);
                    }
                } else {
                    observableEmitter.onNext(null);
                }

            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        DisposableObserver disposable = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                if (!isDisposed()) {
                    if (task != null) {
                        task.onNext(o);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(disposable);

        if (context.getRxContext() != null) {
            context.getRxContext().add(disposable);
        }
    }

    public interface RxTask<T> {
        /**
         * 异步线程调用
         *
         * @param object
         * @return
         */
        T doSth(Object... object);

        /**
         * 结果
         *
         * @param value
         */
        void onNext(T value);
    }
}
