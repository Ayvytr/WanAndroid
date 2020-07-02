package com.ayvytr.wanandroid.ui.square

import androidx.lifecycle.MutableLiveData
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment

/**
 * @author Administrator
 */
class SquareFragment: BaseArticleFragment() {
    override fun loadData(page: Int, isLoadMore: Boolean) {
        mViewModel.getSquareArticle(page, isLoadMore)
    }

    override fun getListLiveData(): MutableLiveData<ResponseWrapper<List<Article>>> {
        return mViewModel.squareLiveData
    }
}