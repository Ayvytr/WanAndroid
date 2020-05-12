package com.ayvytr.wanandroid.ui

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ayvytr.network.ApiClient
import com.ayvytr.network.isNetworkAvailable
import com.ayvytr.wanandroid.api.Api
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.BaseData
import com.ayvytr.wanandroid.bean.MainArticle
import com.ayvytr.wanandroid.db.DbManager

/**
 * @author EDZ
 */

class Repository {
    val api = ApiClient.getInstance().create(Api::class.java)
//    val dao = DbManager.getInstance().db.wanDao()

    suspend fun getMainArticle(page: Int): BaseData<MainArticle> {
        return api.getMainArticle(page)
    }


}