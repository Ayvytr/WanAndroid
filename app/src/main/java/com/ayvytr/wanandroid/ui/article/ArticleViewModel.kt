package com.ayvytr.wanandroid.ui.article

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.PageBean
import com.ayvytr.wanandroid.ui.ArticleRepository

/**
 * @author EDZ
 */
class ArticleViewModel : BaseViewModel() {
    val repository = ArticleRepository()
    val articleLiveData = MutableLiveData<PageBean<Article>>()

    fun getMainArticle(page: Int, isLoadMore: Boolean = false) {
        launchLoading {
            val mainArticle = repository.api.getMainArticle(page)
            if (mainArticle.isFailed()) {
                throw Exception(mainArticle.errorMsg)
            }
            articleLiveData.postValue(
                PageBean(
                    mainArticle.data.curPage,
                    isLoadMore,
                    mainArticle.data.datas,
                    mainArticle.data.hasMore()
                )
            )
        }
    }


}