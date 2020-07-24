package com.ayvytr.wanandroid.ui.webview

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ayvytr.coroutine.BaseActivity
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : BaseActivity<BaseViewModel>() {
    var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_web_view)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                web_view.hideProgress()
                toolbar.setNavigationIcon(if (web_view.canGoBack()) R.drawable.ic_menu_back else android.R.drawable.ic_menu_close_clear_cancel)
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        intent.getStringExtra(TITLE)?.let {
            title = it
        }
        url = intent.getStringExtra(URL)
        url?.let {
            web_view.loadUrl(it)
        }
    }

    override fun onBackPressed() {
        if(web_view.canGoBack()) {
            web_view.goBack()
            return
        }

        super.onBackPressed()
    }

    companion object {
        const val URL = "key_url"
        const val TITLE = "key_title"
    }

}
