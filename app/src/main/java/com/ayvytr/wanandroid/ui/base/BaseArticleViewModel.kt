package com.ayvytr.wanandroid.ui.base

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.network.ApiClient
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.network.wrap
import com.ayvytr.wanandroid.api.Api
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.WxArticleCategory
import com.ayvytr.wanandroid.bean.wrap
import com.ayvytr.wanandroid.copy
import com.ayvytr.wanandroid.db.DbManager
import kotlinx.coroutines.Job

/**
 * @author EDZ
 */
class BaseArticleViewModel : BaseViewModel() {
    val dao = DbManager.getInstance().db.wanDao()

    val api = ApiClient.create(Api::class.java)
    val articleLiveData = MutableLiveData<ResponseWrapper<List<Article>>>()
    val projectLiveData = MutableLiveData<ResponseWrapper<List<Article>>>()
    val squareLiveData = MutableLiveData<ResponseWrapper<List<Article>>>()

    val wxCategoryLiveData = MutableLiveData<ResponseWrapper<List<WxArticleCategory>>>()

    val wxArticleLiveData = MutableLiveData<ResponseWrapper<List<Article>>>()

    val searchKeyLiveData = MutableLiveData<ResponseWrapper<List<Article>>>()

    val askLiveData = MutableLiveData<ResponseWrapper<List<Article>>>()

    val collectListLiveData = MutableLiveData<ResponseWrapper<List<Article>>>()
    val collectLiveData = MutableLiveData<ResponseWrapper<Int>>()

    private val jobMap by lazy {
        hashMapOf<String, Job>()
    }

    fun getMainArticle(page: Int, isLoadMore: Boolean = false) {
        launchWrapper(articleLiveData) {
            api.getMainArticle(page).wrap(isLoadMore)
        }
    }

    fun getProject(page: Int, isLoadMore: Boolean = false) {
        launchWrapper(projectLiveData){
            api.getNewestProject(page).wrap(isLoadMore)
        }
    }

    fun getSquareArticle(page: Int, isLoadMore: Boolean = false) {
        launchWrapper(squareLiveData) {
            api.getSquareArticle(page).wrap(isLoadMore)
        }
    }


    fun getWxArticleCategory() {
        launchWrapper(wxCategoryLiveData) {
            api.getWxArticleCategory().wrap()
        }
    }

    fun getWxArticle(id: Int, page: Int, isLoadMore: Boolean = false) {
        var job = jobMap[wxArticleLiveData.toString()]
        job?.cancel()
        job = launchWrapper(wxArticleLiveData) {
            val wxArticle = api.getWxArticlesById(id, page)
            wxArticle.wrap(isLoadMore)
        }
        jobMap[wxArticleLiveData.toString()] = job
    }

    fun searchWxArticle(id: Int, key: String?, page: Int, isLoadMore: Boolean) {
        launchWrapper(wxArticleLiveData) {
            val wxArticle = api.searchWxArticle(id, page, key)
            wxArticle.wrap(isLoadMore)
        }
    }

    fun getAskArticle(page: Int, isLoadMore: Boolean = false) {
        launchWrapper(askLiveData) {
            api.askArticle(page).wrap(isLoadMore)
        }
    }

    fun searchKey(
        key: String,
        searchAuthor: Boolean,
        page: Int,
        isLoadMore: Boolean
    ) {
        launchWrapper(searchKeyLiveData) {
            val searchKey = if (searchAuthor)
                api.searchArticleByAuthor(page, key)
            else api.searchKey(key, page)
            searchKey.wrap(isLoadMore)
        }
    }

    fun getCollectList(page: Int, isLoadMore: Boolean = false) {
        launchWrapper(collectListLiveData) {
            api.getCollectList(page).wrap(isLoadMore)
        }
    }

    fun collect(id: Int, collect: Boolean, position: Int) {
        launchWrapper(collectLiveData) {
            val value = if (collect) api.cancelCollect(id) else api.collectArticleById(id)
            value.wrap().copy(position)
        }
    }
}
