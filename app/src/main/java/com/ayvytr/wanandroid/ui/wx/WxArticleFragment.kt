package com.ayvytr.wanandroid.ui.wx

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.ktx.ui.isGone
import com.ayvytr.ktx.ui.isShow
import com.ayvytr.logger.L
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.network.exception.ResponseException
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.WxArticleCategory
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.fragment_wx_article.*
import kotlinx.android.synthetic.main.layout_refresh_and_state.*

/**
 * @author Administrator
 */
class WxArticleFragment : BaseArticleFragment() {
    private lateinit var mSearchView: SearchView
    private lateinit var mSearchAutoComplete: SearchView.SearchAutoComplete

    private var lastCategoryIndex = -1

    private val categoryAdapter by lazy {
        WxCategoryAdapter()
    }

    override fun initLiveDataObserver() {
        mViewModel.wxCategoryLiveData.observe(
            this,
            object : WrapperObserver<List<WxArticleCategory>>(this) {
                override fun onSucceed(
                    data: List<WxArticleCategory>,
                    wrapper: ResponseWrapper<List<WxArticleCategory>>
                ) {
                    categoryAdapter.update(data)
                    categoryAdapter.resetIndex()

                    refresh_layout.autoRefresh()
                }

            })

        mViewModel.wxArticleLiveData.observe(this, object : WrapperObserver<List<Article>>(this) {
            override fun onSucceed(data: List<Article>, wrapper: ResponseWrapper<List<Article>>) {
                page = wrapper.page
                mAdapter.update(data, wrapper.isLoadMore)

                if (mAdapter.isEmpty()) {
                    status_view.showEmpty(getString(R.string.search_no_value))
                } else {
                    status_view.showContent()
                }
                refresh_layout.setEnableLoadMore(wrapper.hasMore)

                lastCategoryIndex = categoryAdapter.currentIndex
            }

            override fun onError(
                exception: ResponseException?,
                message: String,
                messageStringId: Int
            ) {
                if (message.isNullOrEmpty()) {

                } else {
                    super.onError(exception, message, messageStringId)
                    if (lastCategoryIndex != -1) {
                        categoryAdapter.currentIndex = lastCategoryIndex
                    }
                }
            }

        })
    }

    override fun loadData(page: Int, isLoadMore: Boolean) {
        if (::mSearchView.isInitialized && mSearchAutoComplete.isFocused) {
            mViewModel.searchWxArticle(
                categoryAdapter.currentCategory(),
                mSearchAutoComplete.text.toString(),
                page,
                isLoadMore
            )
        } else {
            if (categoryAdapter.currentIndex != -1) {
                mViewModel.getWxArticle(categoryAdapter.currentCategory(), page, isLoadMore)
            }
        }
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
            changeCategory(i)
        }
        rv_category.adapter = categoryAdapter

        setHasOptionsMenu(true)
        status_view.showContent()

    }

    private fun changeCategory(i: Int) {
        //第一种方案：正在刷新或者刷新待完成不让切换
        if (i != categoryAdapter.currentIndex && !refresh_layout.isRefreshing && !refresh_layout.state.isFinishing) {
//        if (i != categoryAdapter.currentIndex) {
            categoryAdapter.currentIndex = i

            //下面方案不准确暂不使用
//            if(refresh_layout.state.isFinishing || refresh_layout.state == RefreshState.Refreshing) {
//            if(refresh_layout.state != RefreshState.None) {
//            if((refresh_layout.refreshHeader as ClassicsHeader).isShow()) {
//                L.e("正在刷新")
//                loadData(firstPage)
//            } else {
//                L.e("autoRefresh")
                refresh_layout.autoRefresh()
//            }
        }

    }

    override fun initData(savedInstanceState: Bundle?) {
        firstPage = 1

        mViewModel.getWxArticleCategory()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_wx_article, menu)
        val item = menu.findItem(R.id.mid_search)
        mSearchView = item.actionView as SearchView
        initSearchView()
    }

    private fun initSearchView() {
        mSearchAutoComplete =
            mSearchView.findViewById<View>(R.id.search_src_text) as SearchView.SearchAutoComplete

        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                loadData(firstPage)
                return true
            }
        }
        mSearchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                loadData(firstPage)
            }
        }
        mSearchView.setOnQueryTextListener(queryTextListener)
        mSearchView.queryHint = getString(R.string.search_by_key_hint)
        mSearchView.isIconified = true
    }

    override fun showLoading(isShow: Boolean) {
        if (!isShow) {
            refresh_layout.finishRefresh()
            refresh_layout.finishLoadMore()
        }
    }

    override fun onDestroyView() {
        categoryAdapter.clear()
        rv_category.adapter = null
        rv_category.layoutManager = null
        super.onDestroyView()
    }
}