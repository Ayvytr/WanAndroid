package com.ayvytr.wanandroid.ui.top


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.lifecycle.Observer
import com.ayvytr.adapter.smart
import com.ayvytr.coroutine.BaseCoroutineFragment
import com.ayvytr.ktx.context.dp
import com.ayvytr.ktx.ui.hide
import com.ayvytr.ktx.ui.show
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.loadImage
import com.ayvytr.wanandroid.ui.webview.WebViewActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.DepthPageTransformer
import kotlinx.android.synthetic.main.fragment_top.*
import kotlinx.android.synthetic.main.item_article.view.*
import org.jetbrains.anko.startActivity


/**
 * @author Administrator
 */

class TopFragment : BaseCoroutineFragment<TopViewModel>() {
    private val bannerAdapter by lazy { ImageAdapter(listOf()) }
    private val topArticleAdapter by lazy {
        smart(listOf<Article>(), R.layout.item_article, {
            iv.loadImage(it.envelopePic)
            tv_title.text = it.title.parseAsHtml()
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top, container, false)
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

        rv.adapter = topArticleAdapter

        refresh_layout.setOnRefreshListener {
            mViewModel.getTopArticle()
        }
    }

    override fun onStart() {
        super.onStart()
        if (bannerAdapter.itemCount > 0) {
            banner.start()
        }
    }

    override fun onStop() {
        super.onStop()
        banner.stop()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mViewModel.bannerLiveData.observe(this, Observer {
            if (it.isSucceed) {
                bannerAdapter.setDatas(it.data)
                bannerAdapter.notifyDataSetChanged()
                banner.show()
            } else {
                banner.hide()
            }
        })
        mViewModel.topArticleLiveData.observe(this, Observer {
            if (it.isSucceed) {
                if (it.data.isEmpty()) {
                    status_view.showEmpty()
                } else {
                    topArticleAdapter.update(it.data)
                    status_view.showContent()
                }
            } else {
                status_view.showError(it.error)
            }
        })

        mViewModel.getBanner()
        mViewModel.getTopArticle()
    }

    override fun showLoading(isShow: Boolean) {
        if (isShow) {
            status_view.showLoading()
        } else {
            refresh_layout.finishRefresh()
        }
    }
}