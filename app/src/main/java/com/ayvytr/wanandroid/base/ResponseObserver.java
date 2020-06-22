package com.ayvytr.wanandroid.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.ayvytr.coroutine.bean.ResponseWrapper;

/**
 * @author Administrator
 */
public interface ResponseObserver<T> extends Observer<ResponseWrapper<T>> {
    @Override
    default void onChanged(ResponseWrapper<T> wrapper) {
        if (wrapper.isSucceed()) {
            onSucceed(wrapper.getData());
        } else {
            onError(wrapper.getException(), wrapper.getException().getMessage());
        }
    }

    void onSucceed(@NonNull T data);

    default void onError(@NonNull Throwable exception, @NonNull String message) {

    }
}
