package com.ayvytr.wanandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.bean.ResponseWrapper
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.coroutine.wrapper
import com.ayvytr.network.ApiClient
import com.ayvytr.wanandroid.api.Api
import com.ayvytr.wanandroid.bean.BaseData
import com.ayvytr.wanandroid.bean.UserInfo
import com.ayvytr.wanandroid.bean.wrap

class LoginViewModel : BaseViewModel() {
    val loginLiveData = MutableLiveData<ResponseWrapper<UserInfo>>()
    val registerLiveData = MutableLiveData<ResponseWrapper<UserInfo>>()
    val logoutLiveData = MutableLiveData<ResponseWrapper<Any>>()

    val api = ApiClient.getInstance().create(Api::class.java)

    fun login(username: String, password: String) {
        launchWrapper(loginLiveData) {
            val login = api.login(username, password)
            login.wrap()
        }
    }

    fun logout() {
        launchWrapper(logoutLiveData) {
            api.logout().wrapper()
        }
    }

    fun register(username: String, password: String, repassword: String) {
        launchWrapper(registerLiveData) {
            api.register(username, password, repassword).wrap()
        }
    }
}
