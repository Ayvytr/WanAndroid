package com.ayvytr.wanandroid.db

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * @author Administrator
 */

object Db {
    lateinit var db: AppDatabase
    lateinit var articleDao: ArticleDao

    fun init(context: Context) {
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "db"
        ).allowMainThreadQueries()
            .build()
        articleDao = db.articleDao()
    }

}