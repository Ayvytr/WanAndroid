package com.ayvytr.wanandroid.ui.wx

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import com.ayvytr.coroutine.observer.WrapperListObserver
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.logger.L
import com.ayvytr.network.exception.ResponseException
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.WxArticleCategory
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment
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
                override fun onSucceed(data: List<WxArticleCategory>) {
                    categoryAdapter.update(data)
                    categoryAdapter.resetIndex()

                    refresh_layout.autoRefresh()
                }
            })

        mViewModel.wxArticleLiveData.observe(
            this,
            object : WrapperListObserver<List<Article>>(this) {
                override fun onSucceed(
                    data: List<Article>,
                    page: Int,
                    loadMore: Boolean,
                    hasMore: Boolean
                ) {
                    this@WxArticleFragment.page = page
                    mAdapter.update(data, loadMore)

                    if (mAdapter.isEmpty()) {
                        status_view.showEmpty(getString(R.string.search_no_value))
                    } else {
                        status_view.showContent()
                    }
                    refresh_layout.setEnableLoadMore(hasMore)

                    lastCategoryIndex = categoryAdapter.currentIndex
                }

                override fun onError(exception: ResponseException) {
                    super.onError(exception)
                    if (lastCategoryIndex != -1) {
                        categoryAdapter.currentIndex = lastCategoryIndex
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
        refresh_layout.setEnableAutoLoadMore(false)
        categoryAdapter.itemClickListener = { item, i ->
            changeCategory(i)
        }
        rv_category.adapter = categoryAdapter

        setHasOptionsMenu(true)
        status_view.showContent()

    }

    private fun changeCategory(i: Int) {
        //正在刷新或者刷新待完成不让切换
        if (i != categoryAdapter.currentIndex && !refresh_layout.state.isOpening) {
            categoryAdapter.currentIndex = i
            refresh_layout.autoRefresh()
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