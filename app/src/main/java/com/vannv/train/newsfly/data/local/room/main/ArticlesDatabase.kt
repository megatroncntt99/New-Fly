package com.vannv.train.newsfly.data.local.room.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vannv.train.newsfly.data.local.room.dao.AllNewsRemoteKeyDao
import com.vannv.train.newsfly.data.local.room.dao.PopularArticleDao
import com.vannv.train.newsfly.data.local.room.dao.RecentArticleDao
import com.vannv.train.newsfly.domain.entity.AllNewsRemoteKey
import com.vannv.train.newsfly.domain.entity.PopularArticle
import com.vannv.train.newsfly.domain.entity.RecentArticle
import com.vannv.train.newsfly.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:51 PM
 */

@Database(
    entities = [RecentArticle::class, PopularArticle::class, AllNewsRemoteKey::class],
    version = 1
)
@TypeConverters(RoomConverter::class)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun recentArticleDao(): RecentArticleDao
    abstract fun popularArticleDao(): PopularArticleDao
    abstract fun newsRemoteKeyDao(): AllNewsRemoteKeyDao

    class Callback @Inject constructor(@ApplicationScope private val applicationScope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            applicationScope.launch {

            }
        }
    }

}