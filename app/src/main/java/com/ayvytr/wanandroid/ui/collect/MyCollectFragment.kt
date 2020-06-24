package com.ayvytr.wanandroid.ui.collect

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.PageBean
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment

/**
 * @author Administrator
 */
class MyCollectFragment : BaseArticleFragment() {
    override fun getListLiveData(): MutableLiveData<PageBean<Article>> {
        return mViewModel.collectListLiveData
    }

    override fun loadData(page: Int, isLoadMore: Boolean) {
        mViewModel.getCollectList(page, isLoadMore)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        loadData(0)
    }

}