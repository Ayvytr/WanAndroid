package com.ayvytr.wanandroid.bean

/**
 * @author Administrator
 */
data class ResponseBean<T>(
    val data: T,
    val isSucceed: Boolean = true,
    val error: String = "",
    val hasMore: Boolean = false,
    val page: Int,
    val isLoadMore: Boolean = false
)