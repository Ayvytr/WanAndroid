package com.ayvytr.wanandroid.ui.wx

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.ayvytr.ktx.context.toast
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment
import kotlinx.android.synthetic.main.fragment_wx_article.*

/**
 * @author Administrator
 */
class WxArticleFragment : BaseArticleFragment() {
    private lateinit var mSearchView: SearchView
    private lateinit var mSearchAutoComplete: SearchView.SearchAutoComplete

    private var mKey: String = ""
    private var lastCategoryIndex = -1;

    private val categoryAdapter by lazy {
        WxCategoryAdapter(requireContext())
    }

    override fun loadData(page: Int, isLoadMore: Boolean) {
        if (::mSearchView.isInitialized && mSearchAutoComplete.isFocused) {
            toast(mSearchAutoComplete.text.toString())
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

//    override fun getListLiveData(): MutableLiveData<PageBean<Article>> {
//        return mViewModel.wxArticleLiveData
//    }

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
                changeCategory(i)
            }
        }
        rv_category.adapter = categoryAdapter

        setHasOptionsMenu(true)

    }

    private fun changeCategory(i: Int) {
        categoryAdapter.currentIndex = i
        loadData(firstPage)
    }

    override fun initData(savedInstanceState: Bundle?) {
        firstPage = 1

        mViewModel.wxCategoryLiveData.observe(this, Observer {
            categoryAdapter.update(it)
            categoryAdapter.resetIndex()

            loadData(firstPage)
        })

        mViewModel.wxArticleLiveData.observe(this, Observer {
            if(it.isSucceed) {
                page = it.page
                mAdapter.update(it.data!!, it.isLoadMore)

                if (mAdapter.isEmpty()) {
                    status_view.showEmpty("未搜索到结果")
                } else {
                    status_view.showContent()
                }
                refresh_layout.setEnableLoadMore(it.hasMore)

                lastCategoryIndex = categoryAdapter.currentIndex
            } else {
                if(lastCategoryIndex != -1) {
                    categoryAdapter.currentIndex = lastCategoryIndex
                }
                it.exception?.message?.let {
                    showMessage(it)
                }
            }
        })
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
                mKey = query
                loadData(1)
                return true
            }
        }
        mSearchView.setOnQueryTextListener(queryTextListener)
        mSearchView.queryHint = "输入搜索当前公众号下的文章"
        mSearchView.isIconified = true
    }



}