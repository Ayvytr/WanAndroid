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
}