package com.ayvytr.wanandroid.local

import android.util.Log
import com.ayvytr.ktx.provider.ContextProvider
import com.ayvytr.logger.L
import com.ayvytr.wanandroid.bean.UserInfo
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

/**
 * @author Administrator
 */

object Kv {
    private const val KEY_IS_LOGIN = "key_is_login"
    private const val KEY_USER_INFO = "key_user_info"

    val mmkv: MMKV

    init {
        MMKV.initialize(ContextProvider.getContext())
        mmkv = MMKV.defaultMMKV()
    }

    val gson = Gson()

    fun isLogin(): Boolean {
        return getUserInfo().id.isNotEmpty()
    }

    fun setUserInfo(userInfo: UserInfo) {
        L.e(userInfo)
        mmkv.putString(KEY_USER_INFO, gson.toJson(userInfo))
    }

    fun getUserInfo(): UserInfo {
        val uinfo = mmkv.getString(KEY_USER_INFO, null)
        return if (uinfo == null) {
            UserInfo.ofDefault()
        } else {
            gson.fromJson(uinfo, UserInfo::class.java)
        }
    }

    fun clear() {
        mmkv.clearAll()
    }

}