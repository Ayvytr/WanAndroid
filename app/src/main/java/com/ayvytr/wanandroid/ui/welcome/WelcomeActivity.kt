package com.ayvytr.wanandroid.ui.welcome

import android.os.Bundle
import com.ayvytr.coroutine.BaseActivity
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.ui.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity

class WelcomeActivity : BaseActivity<BaseViewModel>() {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        GlobalScope.launch {
            delay(1000)
            startActivity<MainActivity>()
            finish()
        }
    }
}
