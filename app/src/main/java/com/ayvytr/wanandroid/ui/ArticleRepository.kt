package com.ayvytr.wanandroid.ui

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ayvytr.network.ApiClient
import com.ayvytr.wanandroid.api.Api
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.BaseData

/**
 * @author EDZ
 */

class ArticleRepository {
    val api = ApiClient.getInstance().create(Api::class.java)


//    fun getArticle(): LiveData<PagedList<BaseData<Article>>> {
//        return api.getMainArticle()
//    }

}