package com.ayvytr.wanandroid.ui.base

import android.os.Bundle
import androidx.core.text.parseAsHtml
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.adapter.smart
import com.ayvytr.ktx.ui.show
import com.ayvytr.logger.L
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.PageBean
import com.ayvytr.wanandroid.loadImage
import com.ayvytr.wanandroid.ui.webview.WebViewActivity
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.item_article.view.*
import org.jetbrains.anko.startActivity

/**
 * @author EDZ
 */

open class BaseArticleFragment : BaseListFragment<BaseArticleViewModel, Article>() {

    override fun getViewModelClass(): Class<BaseArticleViewModel> {
        return BaseArticleViewModel::class.java
    }

    open fun getListLiveData(): MutableLiveData<PageBean<Article>> {
        return mViewModel.articleLiveData
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        getListLiveData().observe(this, Observer {
//            articleAdapter.submitList(it)
            page = it.page
            mAdapter.update(it.list, it.isLoadMore)

            if (mAdapter.isEmpty()) {
                status_view.showEmpty("未搜索到结果")
            } else {
                status_view.showContent()
            }
            refresh_layout.setEnableLoadMore(it.hasMore)
        })
        loadData(firstPage)
    }


    override fun getAdapter(): SmartAdapter<Article> {
        return smart(listOf(), R.layout.item_article, {
            iv.loadImage(it.envelopePic)
            tv_title.text = it.title?.parseAsHtml()
            tv_desc.text = it.desc?.parseAsHtml()
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


}

