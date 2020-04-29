package com.ayvytr.wanandroid.ui.article

import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import com.ayvytr.wanandroid.bean.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @author EDZ
 */

class ArticleDataSource(val articleViewModel: ArticleViewModel) :
    BaseIntDataSource<Article>(startPage = 1), CoroutineScope by MainScope() {
    override fun getData(page: Int): List<Article> {
//        val execute = articleViewModel.repository.api.getMainArticle(page).execute()
//        val list = mutableListOf<Article>()
//        runBlocking(articleViewModel.coroutineContext) {
//            val job =
//                articleViewModel.viewModelScope.launch(articleViewModel.mNetworkExceptionHandler) {
//                    list.addAll(articleViewModel.repository.api.getMainArticle(page).data.datas)
//                }
//            job.join()
//        }
//        return list
        return listOf()
    }

    companion object {
        fun ofDataSourceFactory(articleViewModel: ArticleViewModel): DataSource.Factory<Int, Article> {
            return object : DataSource.Factory<Int, Article>() {
                override fun create(): DataSource<Int, Article> {
                    return ArticleDataSource(articleViewModel)
                }
            }
        }
    }
}