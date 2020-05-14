package com.ayvytr.wanandroid

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .error(R.mipmap.ic_launcher)
        .into(this)
}