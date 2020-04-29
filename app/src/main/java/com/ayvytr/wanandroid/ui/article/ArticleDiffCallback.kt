package com.ayvytr.wanandroid.ui.article

import androidx.recyclerview.widget.DiffUtil
import com.ayvytr.wanandroid.bean.Article

/**
 * @author EDZ
 */
class ArticleDiffCallback: DiffUtil.ItemCallback<Article>() {
    override fun getChangePayload(oldItem: Article, newItem: Article): Any? {
        return newItem
    }

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.title == newItem.title &&
                oldItem.link == newItem.link
    }
}