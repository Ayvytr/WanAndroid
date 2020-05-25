package com.ayvytr.wanandroid.bean

/**
 * @author Administrator
 */
data class ResponseBean<T>(
    val data: T,
    val isSucceed: Boolean = true,
    val error: String = "",
    val exception: Exception? = null,
    val hasMore: Boolean = false,
    val page: Int,
    val isLoadMore: Boolean = false
)