package com.ayvytr.wanandroid.ui.top

import android.content.Context
import androidx.core.text.parseAsHtml
import com.ayvytr.ktx.ui.hide
import com.ayvytr.ktx.ui.invisible
import com.ayvytr.ktx.ui.show
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.ui.base.BaseArticleAdapter
import kotlinx.android.synthetic.main.item_article.view.*

/**
 * @author Administrator
 */

open class TopArticleAdapter(
    context: Context,
    collectListener: (Article, Int) -> Unit = { _, _ -> }
) : BaseArticleAdapter(context, collectListener) {

    init {
        map(R.layout.item_article, 0) { it, position ->
            tv_title.text = it.title.parseAsHtml()
            tv_author.text = it.author
            tv_date.text = it.niceDate
            iv_collect.hide()
            iv_collect.isSelected = it.collect
            iv_collect.setOnClickListener { _ ->
                collectListener.invoke(it, position)
            }
            tv_top.show()
        }
    }

}