package com.ayvytr.wanandroid.bean

/**
 * @author Administrator
 */

data class WxArticleCategory(
//    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,

    var selected:Boolean = false
)