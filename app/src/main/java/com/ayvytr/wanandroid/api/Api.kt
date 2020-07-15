package com.ayvytr.wanandroid.api

import com.ayvytr.wanandroid.bean.*
import retrofit2.http.*

/**
 * @author ayvytr
 */
interface Api {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String
    ): BaseData<UserInfo>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): BaseData<UserInfo>

    @GET("user/logout/json")
    suspend fun logout(): BaseData<Unit>

    //首页
    /**
     * 首页文章列表.
     * @param page 0开始
     */
    @GET("article/list/{page}/json")
    suspend fun getMainArticle(@Path("page") page: Int): BaseData<MainArticle>

    /**
     * 首页最新项目.
     * @param page 0开始
     */
    @GET("article/listproject/{page}/json")
    suspend fun getNewestProject(@Path("page") page: Int): BaseData<MainArticle>

    /**
     * 首页banner
     */
    @GET("banner/json")
    suspend fun getBanner(): BaseData<List<Banner>>

    /**
     * 获取公众号列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun getWxArticleCategory(): BaseData<List<WxArticleCategory>>

    /**
     * 查看某个公众号历史数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWxArticlesById(@Path("id") id: Int,
                                  @Path("page") page: Int): BaseData<MainArticle>

    /**
     * 在某个公众号中搜索历史文章
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun searchWxArticle(@Path("id") id: Int,
                                @Path("page") page: Int,
                                @Query("k") key: String?): BaseData<MainArticle>

    /**
     * 常用网站
     */
    @GET("friend/json")
    suspend fun frequentlyWebsites(): BaseData<FrequentlyWebsite>

    /**
     * 目前搜索最多的关键词
     */
    @GET("hotkey/json")
    suspend fun hotkey(): BaseData<HotKey>

    /**
     * 置顶文章
     */
    @GET("article/top/json")
    suspend fun getTopArticles(): BaseData<List<Article>>

    //体系
    /**
     * 体系数据，主要标识的网站内容的体系结构，二级目录。
     */
    @GET("tree/json")
    suspend fun getSystemTree(): BaseData<SystemTree>

    /**
     * 知识体系下的文章
     * @param page 0开始
     */
    @GET("article/list/{page}/json?cid={id}")
    suspend fun getKnowledgeSystemArticle(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): BaseData<MainArticle>

    /**
     * 按照作者昵称搜索文章，不支持模糊匹配。
     * @param page 0开始
     */
    @GET("article/list/{page}/json")
    suspend fun searchArticleByAuthor(
        @Path("page") page: Int,
        @Query("author") author: String
    ): BaseData<MainArticle>

    //导航：暂不提供
    //项目
    /**
     * 项目分类
     */
    @GET("project/tree/json")
    suspend fun getProjectCategory(): BaseData<ProjectCategory>

    /**
     * 某一个分类下项目列表数据，分页展示
     * @param page 从1开始
     */
    @GET("project/list/{page}/json?cid={id}")
    suspend fun getProjectArticle(
        @Path("id") id: String,
        @Path("page") page: Int
    ): BaseData<MainArticle>

    /**
     * 收藏文章列表
     * @param page 从0开始
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): BaseData<MainArticle>

    /**
     * 收藏站内文章
     * @param id 文章id
     */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticleById(@Path("id") id: Int): BaseData<Unit>

    /**
     * 文章列表取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id") id: Int): BaseData<Unit>

    /**
     * 我的收藏页面取消收藏
     * @param id 文章id
     * @param originId 代表的是你收藏之前的那篇文章本身的id； 但是收藏支持主动添加，这种情况下，没有originId
     * 则为-1
     */
    @POST("lg/uncollect/{id}/json")
    suspend fun cancelCollectFromList(@Path("id") id: Int, @Field("originId") originId: Int)

    //收藏网站列表：未整理

    /**
     * 搜索，支持多个关键词，用空格隔开.
     * k ： 搜索关键词
     * @param page 从0开始
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun searchKey(
        @Field("k") key: String,
        @Path("page") page: Int
    ): BaseData<MainArticle>

    //广场
    /**
     * 广场列表数据
     * @param page 从0开始
     */
    @GET("user_article/list/{page}/json")
    suspend fun getSquareArticle(@Path("page") page: Int): BaseData<MainArticle>

//    /**
//     * 分享人对应列表数据,都是审核通过的.
//     * 可能出现返回列表数据<每页数据，因为有自见的文章被过滤掉了。
//     * @param page 从1开始
//     */
//    @GET("user/{uid}/share_articles/{page}/json")
//    suspend fun getUserArticle(
//        @Path("uid") uid: Int,
//        @Path("page") page: Int
//    ): BaseData<UserArticle>
//
//    /**
//     * 自己的分享的文章列表，需要登录
//     * @param page 从1开始
//     */
//    @GET("user/lg/private_articles/{page}/json")
//    suspend fun getSelfArticle(@Path("page") page: Int): BaseData<MainArticle>
//
//    /**
//     * 删除自己分享的文章
//     * @param aid 文章id
//     */
//    @POST("lg/user_article/delete/{aid}/json")
//    suspend fun deleteSelfArticle(@Path("aid") aid: Int): BaseData<Any>
//
//    /**
//     * 分享文章，需要登录，如果为CSDN，简书等链接会直接通过审核，在对外的分享文章列表中展示。
//     * 参数：
//     * title:
//     * link
//     */
//    @POST("lg/user_article/add/json")
//    suspend fun addArticle(@Body body: RequestBody): BaseData<Any>

    /**
     * 问答
     */
    @GET("wenda/list/{page}/json")
    suspend fun askArticle(@Path("page") page: Int): BaseData<MainArticle>
}
