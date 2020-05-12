package com.ayvytr.wanandroid.db

import android.content.Context
import androidx.room.Room

/**
 * @author Administrator
 */

class DbManager private constructor() {
    lateinit var db: AppDatabase

    fun init(context: Context) {
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "db"
        ).build()
    }

    companion object {
        private val INSTANCE = DbManager()

        fun getInstance(): DbManager {
            return INSTANCE
        }

    }
}