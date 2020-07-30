package com.ayvytr.wanandroid

import android.app.Activity
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.ayvytr.ktx.context.getInputMethodManager
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.network.exception.ResponseException
import com.bumptech.glide.Glide

//refreshLayout.getState() == RefreshState.None //空闲状态
//refreshLayout.getState() == RefreshState.Loading//代替 isLoading
//refreshLayout.getState() == RefreshState.Refreshing//代替 isRefreshing
//
//refreshLayout.getState().isDragging //判断是否正在拖拽刷新控件（非拖拽列表）
//refreshLayout.getState().isFinishing //判断动画是否正在结束
//refreshLayout.getState().isHeader //判断当前是否处于 Header 的一系列状态中
//refreshLayout.getState().isFooter //判断当前是否处于 Footer 的一系列状态中
//refreshLayout.getState().isOpening // 等同于 isLoading || isRefreshing

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


fun <T, R> ResponseWrapper<T>.copy(r: R): ResponseWrapper<R> {
    return ResponseWrapper(
        r,
        page,
        isLoadMore,
        hasMore,
        isSucceed,
        ResponseException(message, code, messageStringId, cause)
    )
}

