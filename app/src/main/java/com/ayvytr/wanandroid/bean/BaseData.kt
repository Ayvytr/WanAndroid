package com.ayvytr.wanandroid.bean

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
): ResponseBean<T> {
    return ResponseBean(this.data, !isFailed(), this.errorMsg, null, hasMore, page, isLoadMore)
}