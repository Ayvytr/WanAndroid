package com.ayvytr.wanandroid.ui.collect

import android.os.Bundle
import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.coroutine.observer.WrapperListObserver
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.network.exception.ResponseException
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.ui.base.BaseArticleAdapter
import com.ayvytr.wanandroid.ui.base.BaseArticleViewModel
import com.ayvytr.wanandroid.ui.base.BaseListFragment
import kotlinx.android.synthetic.main.layout_refresh_and_state.*

/**
 * @author Administrator
 */
class MyCollectFragment : BaseListFragment<BaseArticleViewModel, Article>() {

    override fun getAdapter(): SmartAdapter<Article> {
        return BaseArticleAdapter(requireContext()) { it, position ->
            performCollect(it.id, it.collect, position)
        }
    }

    override fun initLiveDataObserver() {
        mViewModel.collectListLiveData.observe(
            this,
            object : WrapperListObserver<List<Article>>(this) {
                override fun onSucceed(
                    data: List<Article>,
                    page: Int,
                    loadMore: Boolean,
                    hasMore: Boolean
                ) {
                    data.forEach { it.collect = true }
                    mAdapter.update(data, loadMore)
                    if (mAdapter.list.isEmpty()) {
                        status_view.showEmpty()
                    } else {
                        status_view.showContent()
                    }
                    finishRefreshAndLoad()
                    refresh_layout.setEnableLoadMore(hasMore)
                }

                override fun onError(exception: ResponseException) {
                    super.onError(exception)
                    finishRefreshAndLoad()
                }
            })
        mViewModel.collectLiveData.observe(this, object : WrapperObserver<Int>() {
            override fun onSucceed(i: Int) {
                mAdapter.removeAt(i)
                showMessage(R.string.canceled_collect)
            }
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        loadData(firstPage)
    }

    override fun loadData(page: Int, isLoadMore: Boolean) {
        mViewModel.getCollectList(page, isLoadMore)
    }

    private fun performCollect(id: Int, collect: Boolean, position: Int) {
        mViewModel.collect(id, collect, position)
    }
}