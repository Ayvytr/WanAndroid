package com.ayvytr.wanandroid.ui.system

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.logger.L
import com.ayvytr.network.ApiClient
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.wanandroid.api.Api
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.SystemTree
import com.ayvytr.wanandroid.bean.wrap
import com.ayvytr.wanandroid.ui.base.BaseArticleViewModel
import kotlinx.coroutines.launch

class SystemViewModel: BaseArticleViewModel() {
    val systemCategoryLiveData = MutableLiveData<ResponseWrapper<List<SystemTree>>>()
    val systemListLiveData = MutableLiveData<ResponseWrapper<List<Article>>>()

    fun getSystemCategory() {
        launchWrapper(systemCategoryLiveData) {
            api.getSystemTree().wrap()
        }
    }

    fun getSystemList(id: Int, page: Int, loadMore: Boolean) {
        launchWrapper(systemListLiveData) {
            api.getKnowledgeSystemArticle(page, id).wrap(loadMore)
        }
    }
}
