package com.ayvytr.wanandroid.welcome

import android.os.Bundle
import com.ayvytr.coroutine.BaseCoroutineActivity
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity

class WelcomeActivity : BaseCoroutineActivity<BaseViewModel>() {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        launch {
            delay(1000)
            startActivity<MainActivity>()
            finish()
        }
    }
}
