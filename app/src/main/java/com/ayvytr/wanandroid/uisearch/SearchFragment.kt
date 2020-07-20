package com.ayvytr.wanandroid.uisearch

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment
import kotlinx.android.synthetic.main.layout_refresh_and_state.*

class SearchFragment() : BaseArticleFragment() {
    private lateinit var mSearchView: SearchView
    private lateinit var mSearchAutoComplete: SearchView.SearchAutoComplete

    private lateinit var menu: Menu

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_refresh_and_state, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setHasOptionsMenu(true)

        status_view.showEmpty(getString(R.string.input_key_to_search))
    }

    override fun initData(savedInstanceState: Bundle?) {
        mViewModel.searchKeyLiveData.observe(this, object : WrapperObserver<List<Article>>(this) {
            override fun onSucceed(data: List<Article>, wrapper: ResponseWrapper<List<Article>>) {
                page = wrapper.page
                mAdapter.update(data, wrapper.isLoadMore)

                if (mAdapter.isEmpty()) {
                    status_view.showEmpty(getString(R.string.search_no_value))
                } else {
                    status_view.showContent()
                }
                refresh_layout.setEnableLoadMore(wrapper.hasMore)
            }
        })
        loadData(firstPage)
    }

    override fun loadData(page: Int, isLoadMore: Boolean) {
        if (::mSearchView.isInitialized && mSearchAutoComplete.isFocused) {
            val isSearchAuthor = menu.findItem(R.id.mid_is_author).isChecked
            val key = mSearchAutoComplete.text.toString()
            mViewModel.searchKey(key, isSearchAuthor, page, isLoadMore)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_fragment, menu)
        this.menu = menu
        val item = menu.findItem(R.id.mid_search)
        mSearchView = item.actionView as SearchView
        initSearchView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mid_is_author -> {
                mSearchView.queryHint =
                    if (item.isChecked)
                        getString(R.string.search_by_nickname_hint)
                    else
                        getString(R.string.search_by_key_hint)
                loadData(0)
                item.isChecked = !item.isChecked
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSearchView() {
        mSearchAutoComplete =
            mSearchView.findViewById<View>(R.id.search_src_text) as SearchView.SearchAutoComplete

        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                loadData(0)
                return true
            }
        }
        mSearchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                loadData(0)
            }
        }
        mSearchView.setOnQueryTextListener(queryTextListener)
        mSearchView.queryHint = getString(R.string.search_by_key_hint)
        mSearchView.isIconified = true
    }
}
