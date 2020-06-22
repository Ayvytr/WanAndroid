package com.ayvytr.wanandroid.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.ayvytr.wanandroid.R

/**
 * @author Administrator
 */
class LoadingDialog(context: Context) :Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
    }
}