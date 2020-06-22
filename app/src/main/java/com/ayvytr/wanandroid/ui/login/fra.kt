package com.ayvytr.wanandroid.ui.login

import android.os.Bundle
import com.ayvytr.coroutine.BaseCoroutineFragment
import com.ayvytr.wanandroid.base.ResponseObserver
import com.ayvytr.wanandroid.bean.UserInfo

/**
 * @author Administrator
 */
class fra : BaseCoroutineFragment<LoginViewModel>() {
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mViewModel.loginLiveData.observe(this, object : ResponseObserver<UserInfo> {
            override fun onSucceed(data: UserInfo) {}
            override fun onError(
                exception: Throwable,
                message: String
            ) {
            }
        })
    }
}