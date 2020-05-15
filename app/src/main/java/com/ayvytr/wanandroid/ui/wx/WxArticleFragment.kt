package com.ayvytr.wanandroid.ui.wx

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayvytr.logger.L
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.PageBean
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment
import kotlinx.android.synthetic.main.fragment_wx_article.*
import kotlinx.android.synthetic.main.fragment_wx_article.refresh_layout
import kotlinx.android.synthetic.main.fragment_wx_article.status_view

/**
 * @author Administrator
 */
class WxArticleFragment : BaseArticleFragment() {

    private val categoryAdapter by lazy {
        WxCategoryAdapter(context!!)
    }

    override fun loadData(page: Int, isLoadMore: Boolean) {
        if (categoryAdapter.currentIndex != -1) {
            mViewModel.getWxArticle(categoryAdapter.currentCategory(), page, isLoadMore)
        }
    }

    override fun getListLiveData(): MutableLiveData<PageBean<Article>> {
        return mViewModel.wxArticleLiveData
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wx_article, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        categoryAdapter.itemClickListener = { item, i ->
            if(categoryAdapter.currentIndex != i) {
                categoryAdapter.currentIndex = i
                status_view.showLoading()
                loadData(firstPage)
            }
        }
        rv_category.adapter = categoryAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        firstPage = 1
        super.initData(savedInstanceState)
        mViewModel.wxCategoryLiveData.observe(this, Observer {
            categoryAdapter.update(it)
            categoryAdapter.resetIndex()

            loadData(firstPage)
        })

        mViewModel.getWxArticleCategory()
    }
}