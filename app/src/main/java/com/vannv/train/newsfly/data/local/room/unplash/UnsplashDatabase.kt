package com.vannv.train.newsfly.data.local.room.unplash

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashImage
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashRemoteKeys

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
@Database(entities = [UnsplashImage::class, UnsplashRemoteKeys::class], version = 1)
abstract class UnsplashDatabase : RoomDatabase() {
    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeyDao(): UnsplashRemoteKeysDao
}