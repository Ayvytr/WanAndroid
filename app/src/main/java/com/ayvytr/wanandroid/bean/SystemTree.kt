package com.ayvytr.wanandroid.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SystemTree(
    val children: List<SystemTree>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    var selected: Boolean
) : Parcelable

