package com.ayvytr.wanandroid.ui.base

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.bean.ResponseWrapper
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.coroutine.wrapper
import com.ayvytr.network.ApiClient
import com.ayvytr.wanandroid.api.Api
import com.ayvytr.wanandroid.bean.*
import com.ayvytr.wanandroid.db.DbManager

/**
 * @author EDZ
 */
class BaseArticleViewModel : BaseViewModel() {
    val api = ApiClient.getInstance().create(Api::class.java)
    val articleLiveData = MutableLiveData<PageBean<Article>>()
    val projectLiveData = MutableLiveData<PageBean<Article>>()
    val squareLiveData = MutableLiveData<PageBean<Article>>()

    val wxCategoryLiveData = MutableLiveData<List<WxArticleCategory>>()

    //    val wxArticleLiveData = MutableLiveData<PageBean<Article>>()
    val wxArticleLiveData = MutableLiveData<ResponseWrapper<List<Article>>>()

    val askLiveData = MutableLiveData<PageBean<Article>>()

    val dao = DbManager.getInstance().db.wanDao()

    fun getMainArticle(page: Int, isLoadMore: Boolean = false) {
        launchLoading {
            val mainArticle = api.getMainArticle(page)
            mainArticle.throwFailedException()
            articleLiveData.postValue(mainArticle.toPageBean(isLoadMore))
        }
    }

    fun getProject(page: Int, isLoadMore: Boolean = false) {
        launchLoading {
            val mainProject = api.getNewestProject(page)
            mainProject.throwFailedException()
            projectLiveData.postValue(mainProject.toPageBean(isLoadMore))
        }
    }

    fun getSquareArticle(page: Int, isLoadMore: Boolean = false) {
        launchLoading {
            val squareArticle = api.getSquareArticle(page)
            squareArticle.throwFailedException()
            squareLiveData.postValue(squareArticle.toPageBean(isLoadMore))
        }
    }


    fun getWxArticleCategory() {
        launchLoading {
            val wxArticleCategory = api.getWxArticleCategory()
            wxArticleCategory.throwFailedException()
            wxCategoryLiveData.postValue(wxArticleCategory.data)
        }
    }

    fun getWxArticle(id: Int, page: Int, isLoadMore: Boolean = false) {
        launchWrapper(wxArticleLiveData) {
            val wxArticle = api.getWxArticlesById(id, page)
            wxArticle.wrap(isLoadMore)
        }
//        launchLoading {
//            val wxArticle = api.getWxArticlesById(id, page)
//            wxArticle.throwFailedException()
//            wxArticleLiveData.postValue(wxArticle.toPageBean(isLoadMore))
//        }
    }

    fun searchWxArticle(id: Int, key: String, page: Int, isLoadMore: Boolean) {
        launchWrapper(wxArticleLiveData) {
            val wxArticle = api.searchWxArticle(id, page, key)
            wxArticle.wrap(isLoadMore)
        }
//        launchLoading {
//            val wxArticle = api.searchWxArticle(id, page, key)
//            wxArticle.throwFailedException()
//            wxArticleLiveData.postValue(wxArticle.toPageBean(isLoadMore))
//        }
    }

    fun getAskArticle(page: Int, isLoadMore: Boolean = false) {
        launchLoading {
            val askArticle = api.askArticle(page)
            askArticle.throwFailedException()
            askLiveData.postValue(askArticle.toPageBean(isLoadMore))
        }
    }

}
