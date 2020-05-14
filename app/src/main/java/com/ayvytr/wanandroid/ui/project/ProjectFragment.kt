package com.ayvytr.wanandroid.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.ayvytr.adapter.smart
import com.ayvytr.coroutine.BaseCoroutineFragment
import com.ayvytr.ktx.ui.show
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.loadImage
import com.ayvytr.wanandroid.ui.webview.WebViewActivity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_artile.*
import kotlinx.android.synthetic.main.item_article.view.*
import org.jetbrains.anko.startActivity

/**
 * @author Administrator
 */

class ProjectFragment : BaseCoroutineFragment<ProjectViewModel>() {
    val adapter by lazy {
        smart(listOf<Article>(), R.layout.item_article, {
            iv.loadImage(it.envelopePic)
            tv_title.text = it.title
            tv_desc.text = it.desc
            tv_desc.show(it.title != it.desc)
        }) {
            itemClick = { t, i ->
                context!!.startActivity<WebViewActivity>(
                    WebViewActivity.URL to t.link,
                    WebViewActivity.TITLE to t.title
                )
            }
            diff()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artile, container, false)
    }

    private var page = 0

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        refresh_layout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mViewModel.getProject(page + 1, true)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mViewModel.getProject(0)
            }
        })
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv.adapter = adapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mViewModel.projectLiveData.observe(this, Observer {
            page = it.page
            adapter.update(it.list, it.isLoadMore)
            status_view.showContent()
            refresh_layout.setEnableLoadMore(it.hasMore)
        })

        mViewModel.getProject(page)
    }

    override fun showLoading(isShow: Boolean) {
        if (isShow) {
            if (adapter.isEmpty()) {
                status_view.showLoading()
            }
        } else {
            refresh_layout.finishRefresh()
            refresh_layout.finishLoadMore()
        }
    }

    override fun showMessage(message: String) {
        super.showMessage(message)
        if (adapter.isEmpty()) {
            status_view.showError(message)
        }
    }
}