package com.ayvytr.wanandroid.bean

import com.ayvytr.network.bean.ResponseWrapper


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
        code = errorCode,
        message = errorMsg,
        exception = Throwable(errorMsg)
    )
}

fun <T> BaseData<T>.wrap(): ResponseWrapper<T> {
    return ResponseWrapper(
        this.data, isSucceed = isSucceed(),
        code = errorCode,
        message = errorMsg,
        exception = if (isFailed()) Exception(errorMsg) else null
    )
}

