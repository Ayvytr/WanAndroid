package com.ayvytr.wanandroid

import android.app.Activity
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.ayvytr.ktx.context.getInputMethodManager
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .error(R.mipmap.ic_launcher)
        .into(this)
}


fun Activity.hideInputMethod() {
    val imm = getInputMethodManager()
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun Fragment.hideInputMethod() {
    val imm = requireContext().getInputMethodManager()
    imm.hideSoftInputFromWindow(requireActivity().window.decorView.windowToken, 0)
}