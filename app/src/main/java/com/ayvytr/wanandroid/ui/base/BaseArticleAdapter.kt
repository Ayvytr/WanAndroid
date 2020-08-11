package com.ayvytr.wanandroid.ui.base

import android.content.Context
import androidx.core.text.parseAsHtml
import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.ktx.ui.show
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.ui.webview.WebViewActivity
import kotlinx.android.synthetic.main.item_article.view.*
import org.jetbrains.anko.startActivity

/**
 * @author Administrator
 */

open class BaseArticleAdapter(
    val context: Context,
    var collectListener: (Article, Int) -> Unit = { _, _ -> }
) : SmartAdapter<Article>() {
    var showTypeAndTag = true

    init {
        map(R.layout.item_article, 0) { it, position ->
            tv_title.text = it.title.parseAsHtml()
            tv_author.text = it.author
            tv_date.text = it.simpleNiceDate()
            tv_type.text = it.superChapterName
            tv_type.show(showTypeAndTag)
            tv_tag.text = it.chapterName
            tv_tag.show(showTypeAndTag)
            iv_collect.isSelected = it.collect
            iv_collect.setOnClickListener { _ ->
                collectListener.invoke(it, position)
            }
        }
        itemClickListener = { t, i ->
            context.startActivity<WebViewActivity>(
                WebViewActivity.URL to t.link,
                WebViewActivity.TITLE to t.title
            )
        }
        diff()
    }

}