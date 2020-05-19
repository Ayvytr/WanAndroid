package com.ayvytr.wanandroid.bean

import java.lang.Exception

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
