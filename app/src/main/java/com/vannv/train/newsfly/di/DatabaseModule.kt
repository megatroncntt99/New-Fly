package com.vannv.train.newsfly.di

import android.app.Application
import androidx.room.Room
import com.vannv.train.newsfly.data.local.room.main.ArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 2:24 PM
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        application: Application,
        callback: ArticlesDatabase.Callback
    ): ArticlesDatabase =
        Room.databaseBuilder(application, ArticlesDatabase::class.java, "article_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

//    @Singleton
//    @Provides
//    fun providesRecentArticleDao(articlesDatabase: ArticlesDatabase) =
//        articlesDatabase.recentArticleDao()
//
//    @Singleton
//    @Provides
//    fun providesPopularArticleDao(articlesDatabase: ArticlesDatabase) =
//        articlesDatabase.popularArticleDao()
//
//    @Singleton
//    @Provides
//    fun providesNewsRemoteKeyDao(articlesDatabase: ArticlesDatabase) =
//        articlesDatabase.newsRemoteKeyDao()


    @ApplicationScope
    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope