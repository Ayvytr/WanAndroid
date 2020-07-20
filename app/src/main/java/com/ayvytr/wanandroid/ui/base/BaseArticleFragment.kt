package com.ayvytr.wanandroid.ui.base

import android.os.Bundle
import androidx.core.text.parseAsHtml
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.adapter.smart
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.ktx.ui.show
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.network.exception.ResponseException
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.loadImage
import com.ayvytr.wanandroid.ui.webview.WebViewActivity
import kotlinx.android.synthetic.main.item_article.view.*
import kotlinx.android.synthetic.main.layout_refresh_and_state.*
import org.jetbrains.anko.startActivity

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
        getListLiveData().observe(this, object : WrapperObserver<List<Article>>(this) {
            override fun onSucceed(data: List<Article>, wrapper: ResponseWrapper<List<Article>>) {
                page = wrapper.page
                mAdapter.update(data, wrapper.isLoadMore)

                if (mAdapter.isEmpty()) {
                    status_view.showEmpty(getString(R.string.search_no_value))
                } else {
                    status_view.showContent()
                }

                refresh_layout.setEnableLoadMore(wrapper.hasMore)
                refresh_layout.finishRefresh()
            }

        })
        mViewModel.collectLiveData.observe(this, object: WrapperObserver<Int>(this) {
            override fun onSucceed(data: Int, wrapper: ResponseWrapper<Int>) {
                val article = mAdapter.list[data]
                article.collect = !article.collect
                mAdapter.notifyItemChanged(data)
            }
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        loadData(firstPage)
    }


    override fun getAdapter(): SmartAdapter<Article> {
        return smart(listOf(), R.layout.item_article, { it, position ->
            iv.loadImage(it.envelopePic)
            tv_title.text = it.title.parseAsHtml()
            tv_desc.text = it.desc?.parseAsHtml()
            tv_desc.show(it.title != it.desc)
            iv_collect.isSelected = it.collect
            iv_collect.setOnClickListener { _ ->
                performCollect(it.id, it.collect, position)
            }
        }) {
            itemClick = { t, i ->
                requireContext().startActivity<WebViewActivity>(
                    WebViewActivity.URL to t.link,
                    WebViewActivity.TITLE to t.title
                )
            }
            diff()
        }
    }

    private fun performCollect(id: Int, isCollect: Boolean, position: Int) {
        mViewModel.collect(id, isCollect, position)
    }


}

