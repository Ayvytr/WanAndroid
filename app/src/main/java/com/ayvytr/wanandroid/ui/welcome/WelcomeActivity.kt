package com.ayvytr.wanandroid.ui.welcome

import android.os.Bundle
import com.ayvytr.coroutine.BaseActivity
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.ui.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity

class WelcomeActivity : BaseActivity<BaseViewModel>() {
    private lateinit var job: Job

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        job = GlobalScope.launch {
            delay(1000)
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
