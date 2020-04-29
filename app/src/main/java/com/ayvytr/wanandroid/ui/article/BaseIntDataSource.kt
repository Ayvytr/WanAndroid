package com.ayvytr.wanandroid.ui.article

import androidx.paging.PageKeyedDataSource

/**
 * @author EDZ
 */
abstract class BaseIntDataSource<T>(val startPage: Int = 1) : PageKeyedDataSource<Int, T>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        callback.onResult(getData(startPage), null, startPage + 1)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        callback.onResult(getData(params.key), params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        callback.onResult(getData(params.key), if (params.key < startPage) null else params.key - 1)
    }

    abstract fun getData(page: Int): List<T>

}