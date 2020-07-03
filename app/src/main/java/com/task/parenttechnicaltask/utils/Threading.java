package com.task.parenttechnicaltask.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class Threading {

    public static void dispatchMain(final Action block) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    block.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static <T> Disposable async(Callable<T> task) {
        return async(task, null, null, Schedulers.io());
    }

    public static <T> Disposable async(Callable<T> task, Consumer<T> finished) {
        return async(task, finished, null, Schedulers.io());
    }

    public static <T> Disposable async(Callable<T> task, Consumer<T> finished, Consumer<Throwable> onError) {
        return async(task, finished, onError, Schedulers.io());
    }

    public static <T> Disposable async(Callable<T> task, Consumer<T> finished, Consumer<Throwable> onError, Scheduler scheduler) {
        finished = finished != null ? finished
                : getEmptyConsumer();
        onError = onError != null ? onError
                : getEmptyThrowableConsumer();

        return Single.fromCallable(task)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(finished, onError);
    }

    public static <T> Disposable async(Observable<T> task) {
        return async(task, null, null, Schedulers.io());
    }

    public static <T> Disposable async(Observable<T> task, Consumer<T> finished) {
        return async(task, finished, null, Schedulers.io());
    }

    public static <T> Disposable async(Observable<T> task, Consumer<T> finished, Consumer<Throwable> onError) {
        return async(task, finished, onError, Schedulers.io());
    }

    public static <T> Disposable async(Observable<T> task, Consumer<T> finished, Consumer<Throwable> onError, Scheduler scheduler) {
        finished = finished != null ? finished
                : getEmptyConsumer();
        onError = onError != null ? onError
                : getEmptyThrowableConsumer();

        return Single.fromObservable(task)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(finished, onError);
    }


    private static <T> Consumer<T> getEmptyConsumer() {

        return new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {

            }
        };
    }

    private static <T> Consumer<Throwable> getEmptyThrowableConsumer() {

        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("async problem", throwable.getMessage());
            }
        };
    }
}

