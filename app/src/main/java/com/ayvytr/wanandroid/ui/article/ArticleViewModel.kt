package com.ayvytr.wanandroid.ui.article

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.PageBean
import com.ayvytr.wanandroid.db.DbManager
import com.ayvytr.wanandroid.ui.Repository

/**
 * @author EDZ
 */
class ArticleViewModel : BaseViewModel() {
    val repository = Repository()
    val articleLiveData = MutableLiveData<PageBean<Article>>()
    val dao = DbManager.getInstance().db.wanDao()

    fun getMainArticle(page: Int, isLoadMore: Boolean = false) {
        launchLoading {
            val mainArticle = repository.getMainArticle(page)
            if (mainArticle.isFailed()) {
//                val articles = dao.getArticles()
//                if(articles.isEmpty()) {
                    throw Exception(mainArticle.errorMsg)
//                } else {
//                    mainArticle.data.datas = articles
//                }
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