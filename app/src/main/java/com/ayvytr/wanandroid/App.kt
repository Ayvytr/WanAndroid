package com.ayvytr.wanandroid

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import com.ayvytr.ktx.provider.ContextProvider
import com.ayvytr.network.ApiClient
import com.ayvytr.wanandroid.db.Db
import com.bumptech.glide.Glide
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * @author ayvytr
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
//        if(isMainProcess()) {
        CrashReport.initCrashReport(applicationContext, "c9c54ee454", BuildConfig.DEBUG);

        GlobalScope.launch {

            ApiClient.init(
                Const.WANANDROID_BASE_URL, 20, cache = null,
                enableCookieJar = true
            )
//            ApiClient.logInterceptor.showLog = false
            Db.init(applicationContext)
            MMKV.initialize(ContextProvider.getContext())
            Glide.with(applicationContext)

        }
//        }
    }

    /**
     * 获取当前进程名
     */
    private fun getCurrentProcessName(): String {
        val pid = Process.myPid()
        var processName = ""
        val manager =
            applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid == pid) {
                processName = process.processName
            }
        }
        return processName
    }

    /**
     * 包名判断是否为主进程
     *
     * @param
     * @return
     */
    fun isMainProcess(): Boolean {
        return applicationContext.packageName == getCurrentProcessName()
    }
}