package com.ayvytr.wanandroid.ui

import com.ayvytr.network.ApiClient
import com.ayvytr.wanandroid.api.Api
import com.ayvytr.wanandroid.bean.BaseData
import com.ayvytr.wanandroid.bean.MainArticle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * @author EDZ
 */

class Repository : CoroutineScope by MainScope() {
    val api = ApiClient.create(Api::class.java)
//    val dao = DbManager.getInstance().db.wanDao()

    suspend fun getMainArticle(page: Int): BaseData<MainArticle> {
        return api.getMainArticle(page)
    }

    suspend fun getMainProject(page: Int): BaseData<MainArticle> {
        return api.getNewestProject(page)
    }


}