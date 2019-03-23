package com.test.rxjava.utils;

import com.test.rxjava.interfaces.IRxJavaLifeCycle;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    private static final String TAG = RxUtil.class.getSimpleName();

    public static void io(IRxJavaLifeCycle context, RxTask task, Object... objects) {


        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
                if (observableEmitter.isDisposed()) {
                    return;
                }

                if (task != null) {
                    Object value = task.doSth(observableEmitter, objects);
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

        if (context.getRxLifeCycle() != null) {
            context.getRxLifeCycle().add(disposable);
        }
    }

    public interface RxTask<T> {
        /**
         * 异步线程调用
         *
         * @param emitter 发送器，由于长时间线程中判断订阅关系是否已经切断
         * @param object
         * @return
         */
        T doSth(ObservableEmitter emitter, Object... object);

        /**
         * 结果
         *
         * @param value
         */
        void onNext(T value);
    }
}
