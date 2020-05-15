package com.ayvytr.wanandroid.ui.article

import com.ayvytr.wanandroid.ui.base.BaseArticleFragment

/**
 * @author EDZ
 */

class ArticleFragment : BaseArticleFragment() {

    override fun loadData(page: Int, isLoadMore: Boolean) {
        mViewModel.getMainArticle(page, isLoadMore)
    }

}

