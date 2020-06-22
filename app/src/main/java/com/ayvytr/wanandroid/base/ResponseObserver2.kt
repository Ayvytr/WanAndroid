package com.ayvytr.wanandroid.base

import androidx.lifecycle.Observer
import com.ayvytr.coroutine.bean.ResponseWrapper

/**
 * @author Administrator
 */
abstract class ResponseObserver2<T>(
    private val onSucceed: (T) -> Unit,
    private val onError: (Throwable, String) -> Unit = { _: Throwable, _: String -> }
) :
    Observer<ResponseWrapper<T>> {
    override fun onChanged(wrapper: ResponseWrapper<T>) {
        if (wrapper.isSucceed) {
            onSucceed(wrapper.data!!)
        } else {
            onError(wrapper.exception!!, wrapper.exception!!.message!!)
        }
    }

}