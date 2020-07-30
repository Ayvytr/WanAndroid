package com.ayvytr.wanandroid.ui.system

import android.os.Bundle
import androidx.core.text.parseAsHtml
import androidx.navigation.fragment.navArgs
import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.adapter.smart
import com.ayvytr.coroutine.observer.WrapperListObserver
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.ktx.ui.setActivityTitle
import com.ayvytr.logger.L
import com.ayvytr.network.exception.ResponseException
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.ui.base.BaseListFragment
import com.ayvytr.wanandroid.ui.webview.WebViewActivity
import kotlinx.android.synthetic.main.item_article_backup.view.*
import kotlinx.android.synthetic.main.layout_refresh_and_state.*
import org.jetbrains.anko.startActivity

/**
 * @author Administrator
 */
class SystemListFragment : BaseListFragment<SystemViewModel, Article>() {
    val args: SystemListFragmentArgs by navArgs()

    override fun getAdapter(): SmartAdapter<Article> {
        return smart(listOf(), R.layout.item_article_backup, { it, position ->
            tv_title.text = it.title.parseAsHtml()
            tv_author.text = it.author
            tv_date.text = it.niceShareDate
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
                mAdapter.notifyItemChanged(data)
            }
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setActivityTitle("${args.category}-${args.systemTree.name}")
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        loadData(firstPage)
    }

    override fun loadData(page: Int, isLoadMore: Boolean) {
        mViewModel.getSystemList(args.systemTree.id, page, isLoadMore)
    }

    private fun performCollect(id: Int, collect: Boolean, position: Int) {
        mViewModel.collect(id, collect, position)
    }

}
