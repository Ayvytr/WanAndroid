package com.ayvytr.wanandroid.ui.article

import android.os.Bundle
import androidx.core.text.parseAsHtml
import com.ayvytr.adapter.smart
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.ktx.context.dp
import com.ayvytr.ktx.ui.hide
import com.ayvytr.ktx.ui.show
import com.ayvytr.network.exception.ResponseException
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.Banner
import com.ayvytr.wanandroid.loadImage
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment
import com.ayvytr.wanandroid.ui.top.ImageAdapter
import com.ayvytr.wanandroid.ui.webview.WebViewActivity
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.DepthPageTransformer
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.item_article.view.*
import org.jetbrains.anko.startActivity

/**
 * @author EDZ
 */

class ArticleFragment : BaseArticleFragment() {
    private val bannerAdapter by lazy { ImageAdapter(listOf()) }
    private val topArticleAdapter by lazy {
        smart(listOf<Article>(), R.layout.item_article, { it, _->
            iv.loadImage(it.envelopePic)
            tv_title.text = it.title.parseAsHtml()
            tv_desc.text = it.desc?.parseAsHtml()
            tv_desc.show(it.title != it.desc)
        }) {
            itemClick = { t, i ->
                context?.startActivity<WebViewActivity>(
                    WebViewActivity.URL to t.link,
                    WebViewActivity.TITLE to t.title
                )
            }
            diff()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_article
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        banner.adapter = bannerAdapter
        banner.setIndicator(CircleIndicator(context))
        banner.setIndicatorSelectedColorRes(R.color.colorPrimary)
        banner.setIndicatorNormalColorRes(R.color.white)
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER)
        banner.setIndicatorSpace(10.dp)
        banner.setIndicatorMargins(IndicatorConfig.Margins(5.dp))
        banner.setIndicatorWidth(10.dp, 20.dp)
        banner.setPageTransformer(DepthPageTransformer())
        banner.start()

        rv_top.adapter = topArticleAdapter
    }

    override fun initLiveDataObserver() {
        super.initLiveDataObserver()
        mViewModel.bannerLiveData.observe(this, object: WrapperObserver<List<Banner>>(this) {
            override fun onSucceed(data: List<Banner>) {
                bannerAdapter.setDatas(data)
                bannerAdapter.notifyDataSetChanged()
                banner.show()
            }

            override fun onError(exception: ResponseException) {
                super.onError(exception)
                if (bannerAdapter.itemCount == 0) {
                    banner.hide()
                }
            }
        })
        mViewModel.topArticleLiveData.observe(this, object : WrapperObserver<List<Article>>(this) {
            override fun onSucceed(data: List<Article>) {
                if (data.isEmpty()) {
//                    status_view.showEmpty()
                } else {
                    topArticleAdapter.update(data)
//                    status_view.showContent()
                }
                refresh_layout.finishRefresh()
            }

            override fun onError(exception: ResponseException) {
                super.onError(exception)
                refresh_layout.finishRefresh()
            }
        })
    }

    override fun loadData(page: Int, isLoadMore: Boolean) {
        mViewModel.getMainArticle(page, isLoadMore)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mViewModel.getBanner()
        mViewModel.getTopArticle()
    }
}

