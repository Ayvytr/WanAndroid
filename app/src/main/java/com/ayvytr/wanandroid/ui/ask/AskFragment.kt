package com.ayvytr.wanandroid.ui.ask

import androidx.lifecycle.MutableLiveData
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.PageBean
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment

/**
 * @author Administrator
 */

class AskFragment : BaseArticleFragment() {
    override fun loadData(page: Int, isLoadMore: Boolean) {
        mViewModel.getAskArticle(page, isLoadMore)
    }

    override fun getListLiveData(): MutableLiveData<PageBean<Article>> {
        return mViewModel.askLiveData
    }
}