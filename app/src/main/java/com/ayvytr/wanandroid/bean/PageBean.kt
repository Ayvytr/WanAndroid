package com.ayvytr.wanandroid.bean

/**
 * @author EDZ
 */

data class PageBean<T>(
    val page: Int,
    val isLoadMore: Boolean = false,
    val list: List<T> = listOf(),
    val hasMore: Boolean = false
)