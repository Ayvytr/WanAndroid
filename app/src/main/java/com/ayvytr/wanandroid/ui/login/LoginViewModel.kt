package com.ayvytr.wanandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.logger.L
import com.ayvytr.network.ApiClient
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.wanandroid.api.Api
import com.ayvytr.wanandroid.bean.UserInfo
import com.ayvytr.wanandroid.bean.wrap
import com.ayvytr.wanandroid.local.Kv

class LoginViewModel : BaseViewModel() {
    val loginLiveData = MutableLiveData<ResponseWrapper<UserInfo>>()
    val registerLiveData = MutableLiveData<ResponseWrapper<UserInfo>>()
    val logoutLiveData = MutableLiveData<ResponseWrapper<Unit>>()

    val api = ApiClient.create(Api::class.java)

    fun login(username: String, password: String) {
        launchWrapper(loginLiveData) {
            val login = api.login(username, password)
            login.wrap()
        }
    }

    fun logout() {
        L.e("logout")
        launchWrapper(logoutLiveData) {
            L.e("logout n call")
            val result = api.logout()
            ApiClient.cookieJar.clear()
            Kv.clear()
            result.wrap()
        }
    }

    fun register(username: String, password: String, repassword: String) {
        launchWrapper(registerLiveData) {
            api.register(username, password, repassword).wrap()
        }
    }
}
