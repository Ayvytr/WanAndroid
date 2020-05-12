package com.ayvytr.wanandroid.ui.article

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
import com.ayvytr.wanandroid.ui.webview.WebViewActivity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_artile.*
import kotlinx.android.synthetic.main.item_article.view.*
import org.jetbrains.anko.startActivity

/**
 * @author EDZ
 */

class ArticleFragment : BaseCoroutineFragment<ArticleViewModel>() {
    //    val articleAdapter = ArticleAdapter()
    val articleAdapter by lazy {
        smart(listOf<Article>(), R.layout.item_article, {
            tv_title.text = it.title
            tv_desc.text = it.desc
            tv_desc.show(it.title != it.desc)
        }) {
            itemClick = { t, i ->
                context!!.startActivity<WebViewActivity>(WebViewActivity.URL to t.link,
                                                         WebViewActivity.TITLE to t.title)
            }
        }
    }

    var page: Int = 0
//    val articleAdapter = smart(listOf<Article>(), R.layout.item_article, {
//        tv_title.text = it.title
//        tv_desc.text = it.desc
//    }) {
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artile, container, false)
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
//        val smart = smart(listOf<Article>(), R.layout.item_article, {
//            tv_title.text = it.title
//            tv_desc.text = it.desc
//        }) {
//        }
        rv.adapter = articleAdapter
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        refresh_layout.setOnRefreshListener {
            mViewModel.getMainArticle(1)
        }
        refresh_layout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mViewModel.getMainArticle(page+1, true)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mViewModel.getMainArticle(0)
            }

        })
        refresh_layout
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mViewModel.articleLiveData.observe(this, Observer {
//            articleAdapter.submitList(it)
            page = it.page
            articleAdapter.update(it.list, it.isLoadMore)

            status_view.showContent()
            refresh_layout.setEnableLoadMore(it.hasMore)
        })
        mViewModel.getMainArticle(page, true)
    }

    override fun getViewModelClass(): Class<ArticleViewModel> {
        return ArticleViewModel::class.java
    }

    override fun showLoading(isShow: Boolean) {
        if(isShow) {
            if(articleAdapter.isEmpty()) {
                status_view.showLoading()
            }
        }
        else {
            refresh_layout.finishRefresh()
            refresh_layout.finishLoadMore()
        }
    }

    override fun showMessage(message: String) {
        super.showMessage(message)
        if(articleAdapter.isEmpty()) {
            status_view.showError(message)
        }
    }
}