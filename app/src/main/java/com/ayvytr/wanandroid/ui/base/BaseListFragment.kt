package com.ayvytr.wanandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.coroutine.BaseCoroutineFragment
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_article.*

/**
 * @author Administrator
 */
abstract class BaseListFragment<T : BaseViewModel, B> : BaseCoroutineFragment<T>() {
    lateinit var mAdapter: SmartAdapter<B>

    protected var firstPage = 0
    protected var page = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        refresh_layout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                loadData(page + 1, true)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                loadData(firstPage)
            }
        })
        mAdapter = getAdapter()
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv.adapter = mAdapter
    }

    abstract fun getAdapter(): SmartAdapter<B>

    open fun loadData(page: Int, isLoadMore: Boolean = false) {

    }

    override fun showLoading(isShow: Boolean) {
        if (isShow) {
            if (mAdapter.isEmpty()) {
                status_view.showLoading()
            }
        } else {
            refresh_layout.finishRefresh()
            refresh_layout.finishLoadMore()
        }
    }

    override fun showMessage(message: String) {
        super.showMessage(message)
        if (mAdapter.isEmpty()) {
            status_view.showError(message)
        }
    }

}