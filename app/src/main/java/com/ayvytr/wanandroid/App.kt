package com.ayvytr.wanandroid

import android.app.Application
import com.ayvytr.network.ApiClient

/**
 * @author ayvytr
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.getInstance().init(Const.WANANDROID_BASE_URL, 20, cache=null)
    }
}