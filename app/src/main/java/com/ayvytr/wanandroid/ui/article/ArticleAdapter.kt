package com.ayvytr.wanandroid.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article

/**
 * @author EDZ
 */

class ArticleAdapter : PagedListAdapter<Article, ArticleAdapter.Vh>(ArticleDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        return holder.bind(getItem(position), position)
    }

    class Vh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        val tvAuthor: TextView = itemView.findViewById(R.id.tv_author)
        fun bind(item: Article?, position: Int) {
            item?.let {
                tvTitle.text = it.title
                tvDesc.text = it.desc
                tvAuthor.text = it.author
            }
        }

    }
}