package com.ayvytr.wanandroid.db

import androidx.room.Dao
import androidx.room.Query
import com.ayvytr.wanandroid.bean.Article

/**
 * @author Administrator
 */
@Dao
interface WanDao {
    @Query("select * from article")
    fun getArticles():List<Article>
}