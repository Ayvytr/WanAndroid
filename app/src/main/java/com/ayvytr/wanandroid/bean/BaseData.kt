package com.ayvytr.wanandroid.bean

import com.ayvytr.coroutine.bean.ResponseWrapper

/**
 * @author ayvytr
 */
data class BaseData<T>(
    val `data`: T,
    val errorCode: Int,
    val errorMsg: String
) {
    fun isFailed(): Boolean {
        return errorCode != 0
    }

    fun isSucceed(): Boolean {
        return !isFailed()
    }

    fun throwFailedException() {
        if(isFailed()) {
            throw Exception(errorMsg)
        }
    }
}

fun BaseData<MainArticle>.toPageBean(isLoadMore: Boolean = false): PageBean<Article> {
    return PageBean(data.curPage, isLoadMore, data.datas, data.hasMore())
}

fun <T> BaseData<T>.toResponseWrapper(
    page: Int = 1,
    isLoadMore: Boolean = false,
    hasMore: Boolean = false
): ResponseWrapper<T> {
    return ResponseWrapper(this.data, !isFailed(), page, isLoadMore, hasMore, Throwable(errorMsg))
}

fun BaseData<MainArticle>.wrap(
    isLoadMore: Boolean = false
): ResponseWrapper<List<Article>> {
    return ResponseWrapper(this.data.datas, !isFailed(), data.curPage, isLoadMore, data.hasMore(), Throwable(errorMsg))
}