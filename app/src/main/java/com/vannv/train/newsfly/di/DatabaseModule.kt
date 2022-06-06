package com.vannv.train.newsfly.di

import android.content.Context
import androidx.room.Room
import com.vannv.train.newsfly.data.local.room.NewDao
import com.vannv.train.newsfly.data.local.room.NewDatabase
import com.vannv.train.newsfly.data.local.room.unplash.UnsplashDatabase
import com.vannv.train.newsfly.data.local.room.unplash.UnsplashImageDao
import com.vannv.train.newsfly.data.local.room.unplash.UnsplashRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideNewDatabase(@ApplicationContext context: Context): NewDatabase {
        return Room.databaseBuilder(context, NewDatabase::class.java, "new_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewDao(newDatabase: NewDatabase): NewDao = newDatabase.newDao()

    @Singleton
    @Provides
    fun provideUnsplashDatabase(@ApplicationContext context: Context): UnsplashDatabase {
        return Room.databaseBuilder(context, UnsplashDatabase::class.java, "unsplash_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUnsplashImageDao(unsplashDatabase: UnsplashDatabase): UnsplashImageDao {
        return unsplashDatabase.unsplashImageDao()
    }

    @Singleton
    @Provides
    fun provideUnsplashRemoteKeysDao(unsplashDatabase: UnsplashDatabase): UnsplashRemoteKeysDao {
        return unsplashDatabase.unsplashRemoteKeyDao()
    }
}