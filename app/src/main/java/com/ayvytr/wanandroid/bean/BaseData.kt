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

    fun throwFailedException() {
        if(isFailed()) {
            throw Exception(errorMsg)
        }
    }
}

fun BaseData<MainArticle>.toPageBean(isLoadMore: Boolean = false): PageBean<Article> {
    return PageBean(data.curPage, isLoadMore, data.datas, data.hasMore())
}

fun <T> BaseData<T>.toResponse(
    hasMore: Boolean = false,
    page: Int = 1,
    isLoadMore: Boolean = false
): ResponseWrapper<T> {
    return ResponseWrapper(this.data, !isFailed(), page, isLoadMore, hasMore, Throwable(errorMsg))
}