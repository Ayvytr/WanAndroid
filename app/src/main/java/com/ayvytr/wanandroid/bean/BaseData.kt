package com.ayvytr.wanandroid.bean

import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.network.exception.ResponseException


/**
 * @author ayvytr
 */
data class BaseData<T>(
    val `data`: T?,
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
        if (isFailed()) {
            throw Exception(errorMsg)
        }
    }
}

fun <T> ResponseWrapper<T>.isNotLogin(): Boolean {
    return code == -1001
}

fun BaseData<MainArticle>.wrap(
    isLoadMore: Boolean = false
): ResponseWrapper<List<Article>> {
    return ResponseWrapper(
        this.data?.datas,
        data?.curPage ?: 0,
        isLoadMore,
        data?.hasMore() ?: false,
        isSucceed(),
        ResponseException(errorMsg, errorCode, cause = Throwable(errorMsg))
    )
}

fun <T> BaseData<T>.wrap(): ResponseWrapper<T> {
    return ResponseWrapper(
        this.data, isSucceed = isSucceed(),
        exception = ResponseException(
            errorMsg,
            errorCode,
            cause = if (isFailed()) Exception(errorMsg) else null
        )
    )
}

