package com.ayvytr.wanandroid.ui.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.coroutine.observer.WrapperListObserver
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import kotlinx.android.synthetic.main.layout_refresh_and_state.*

/**
 * @author EDZ
 */

open class BaseArticleFragment : BaseListFragment<BaseArticleViewModel, Article>() {

    override fun getViewModelClass(): Class<BaseArticleViewModel> {
        return BaseArticleViewModel::class.java
    }

    open fun getListLiveData(): MutableLiveData<ResponseWrapper<List<Article>>> {
        return mViewModel.articleLiveData
    }

    override fun initLiveDataObserver() {
        super.initLiveDataObserver()
        getListLiveData().observe(this, object : WrapperListObserver<List<Article>>(this) {
            override fun onSucceed(
                data: List<Article>,
                p: Int,
                loadMore: Boolean,
                hasMore: Boolean
            ) {
                page = p
                mAdapter.update(data, loadMore)

                if (mAdapter.isEmpty()) {
                    status_view.showEmpty(getString(R.string.search_no_value))
                } else {
                    status_view.showContent()
                }

                refresh_layout.setEnableLoadMore(hasMore)
                refresh_layout.finishRefresh()
            }

        })
        mViewModel.collectLiveData.observe(this, object: WrapperObserver<Int>(this) {
            override fun onSucceed(data: Int) {
                val article = mAdapter.list[data]
                article.collect = !article.collect
                showMessage(if(article.collect) R.string.collected else R.string.canceled_collect)
                mAdapter.notifyItemChanged(data)
            }
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        loadData(firstPage)
    }


    override fun getAdapter(): SmartAdapter<Article> {
        return BaseArticleAdapter(requireContext()) {it, position->
            performCollect(it.id, it.collect, position)
        }
    }

    fun performCollect(id: Int, isCollect: Boolean, position: Int) {
        mViewModel.collect(id, isCollect, position)
    }


}

