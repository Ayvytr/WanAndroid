package com.ayvytr.wanandroid.ui.top

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.network.ApiClient
import com.ayvytr.wanandroid.api.Api
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.Banner
import com.ayvytr.wanandroid.bean.ResponseBean
import com.ayvytr.wanandroid.bean.toResponse

class TopViewModel : BaseViewModel() {
    val api = ApiClient.getInstance().create(Api::class.java)

    val bannerLiveData = MutableLiveData<ResponseBean<List<Banner>>>()
    val topArticleLiveData = MutableLiveData<ResponseBean<List<Article>>>()

    fun getBanner() {
        launchLoading {
            val banner = api.getBanner()
            bannerLiveData.postValue(banner.toResponse())
        }
    }

    fun getTopArticle() {
        launchLoading {
            val topArticles = api.getTopArticles()
            topArticles.throwFailedException()
            topArticleLiveData.postValue(topArticles.toResponse())
        }
    }
}
