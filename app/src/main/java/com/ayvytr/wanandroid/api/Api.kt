package com.ayvytr.wanandroid.api

import com.ayvytr.wanandroid.bean.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author ayvytr
 */
interface Api {
    //登录
//    @POST("user/login")
//    suspend fun login(@Body login: LoginReq)
//
//    @POST("user/register")
//    suspend fun register(@Body register: RegisterReq)
//
//    @GET("user/logout/json")
//    suspend fun logout()

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
    suspend fun getBanner(): BaseData<Banner>

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
    suspend fun getTopArticles(): BaseData<TopArticle>

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
    @GET("article/list/{page}/json?author={author}")
    suspend fun searchArticleByAuthor(
        @Path("author") author: String,
        @Path("page") page: Int
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

    //收藏：暂不提供
    //搜索
    /**
     * 支持多个关键词，用空格隔开.
     * k ： 搜索关键词
     * @param page 从0开始
     */
    @POST("article/query/{page}/json")
    //TODO: 修正返回值
    suspend fun search(@Body body: RequestBody, @Path("page") page: Int): BaseData<MainArticle>

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

    //问答：暂不提供
}
