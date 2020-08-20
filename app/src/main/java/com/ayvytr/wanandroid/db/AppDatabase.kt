package com.ayvytr.wanandroid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayvytr.wanandroid.bean.Article

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}