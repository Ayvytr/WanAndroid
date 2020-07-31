package com.ayvytr.wanandroid.ui.system

import android.os.Bundle
import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.coroutine.observer.WrapperListObserver
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.ktx.ui.setActivityTitle
import com.ayvytr.network.exception.ResponseException
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.argNotNull
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.SystemTree
import com.ayvytr.wanandroid.ui.base.BaseArticleAdapter
import com.ayvytr.wanandroid.ui.base.BaseListFragment
import kotlinx.android.synthetic.main.layout_refresh_and_state.*

/**
 * @author Administrator
 */
class SystemListFragment : BaseListFragment<SystemViewModel, Article>() {
//    val args: SystemListFragmentArgs by navArgs()

    val category by argNotNull<String>("category")
    val systemTree by argNotNull<SystemTree>("systemTree")

    override fun getAdapter(): SmartAdapter<Article> {
        return BaseArticleAdapter(requireContext()) { it, position ->
            performCollect(it.id, it.collect, position)
        }
    }

    override fun initLiveDataObserver() {
        mViewModel.systemListLiveData.observe(
            this,
            object : WrapperListObserver<List<Article>>(this) {
                override fun onSucceed(
                    data: List<Article>,
                    page: Int,
                    loadMore: Boolean,
                    hasMore: Boolean
                ) {
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
            override fun onSucceed(data: Int) {
                val article = mAdapter.list[data]
                article.collect = !article.collect
                showMessage(if(article.collect) R.string.collected else R.string.canceled_collect)
                mAdapter.notifyItemChanged(data)
            }
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setActivityTitle("${category}-${systemTree.name}")
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        loadData(firstPage)
    }

    override fun loadData(page: Int, isLoadMore: Boolean) {
        mViewModel.getSystemList(systemTree.id, page, isLoadMore)
    }

    private fun performCollect(id: Int, collect: Boolean, position: Int) {
        mViewModel.collect(id, collect, position)
    }

}
