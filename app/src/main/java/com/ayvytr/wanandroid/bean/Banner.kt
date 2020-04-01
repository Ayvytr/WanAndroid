package com.ayvytr.wanandroid.bean


/**
 * @author ayvytr
 */

data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)