package com.ayvytr.wanandroid.bean

data class UserArticle(
    val coinInfo: CoinInfo,
    val shareArticles: ShareArticles
)

data class CoinInfo(
    val coinCount: Int,
    val level: Int,
    val rank: Int,
    val userId: Int,
    val username: String
)

data class ShareArticles(
    val curPage: Int,
    val datas: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

