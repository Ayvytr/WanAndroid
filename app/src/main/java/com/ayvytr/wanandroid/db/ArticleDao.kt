package com.ayvytr.wanandroid.db

import androidx.annotation.IntRange
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayvytr.wanandroid.bean.Article

/**
 * @author Administrator
 */
@Dao
interface ArticleDao {
    @Query("select * from article")
    fun getArticles(): List<Article>

    @Query("select * from article limit ((:page-1)*10),:limit")
    fun getArticleLimit(
        @IntRange(from = 1, to = Long.MAX_VALUE) page: Int,
        limit: Int = 10
    ): List<Article>

    @Query("select * from article where id=:id")
    fun getById(id: Int): Article

    @Query("select count(*) from article where id=:id")
    fun countById(id: Int): Int

    fun exists(id: Int): Boolean {
        return countById(id) != 0
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: Article)
}