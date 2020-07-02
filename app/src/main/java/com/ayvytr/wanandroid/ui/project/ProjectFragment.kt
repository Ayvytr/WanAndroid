package com.ayvytr.wanandroid.ui.project

import androidx.lifecycle.MutableLiveData
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment

/**
 * @author Administrator
 */

class ProjectFragment : BaseArticleFragment() {

    override fun loadData(page: Int, isLoadMore: Boolean) {
        mViewModel.getProject(page, isLoadMore)
    }

    override fun getListLiveData(): MutableLiveData<ResponseWrapper<List<Article>>> {
        return mViewModel.projectLiveData
    }
}