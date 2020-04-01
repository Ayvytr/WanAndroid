package com.ayvytr.wanandroid.bean

/**
 * @author ayvytr
 */
data class BaseData<T>(
    val `data`: List<T>,
    val errorCode: Int,
    val errorMsg: String
)