package com.ayvytr.wanandroid

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ayvytr.ktx.context.getInputMethodManager
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .error(R.mipmap.ic_launcher)
        .into(this)
}

fun Fragment.setActivityTitle(@StringRes resId: Int) {
    setActivityTitle(getString(resId))
}

/**
 * 设置Activity标题，直接调用[Fragment.requireActivity.setTitle]，不起作用（使用了Navigation）.
 */
fun Fragment.setActivityTitle(title: String) {
    val activity = requireActivity()
    when (activity) {
        is AppCompatActivity -> {
                activity.supportActionBar?.setTitle(title)
        }
        else -> activity.actionBar?.title = title
    }
}

fun Activity.hideInputMethod() {
    val imm = getInputMethodManager()
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun Fragment.hideInputMethod() {
    val imm = requireContext().getInputMethodManager()
    imm.hideSoftInputFromWindow(requireActivity().window.decorView.windowToken, 0)
}