package com.vannv.train.newsfly.di

import com.vannv.train.newsfly.data.local.datastore.DataStorage
import com.vannv.train.newsfly.data.local.datastore.DataStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DatastoreModule {
    @Singleton
    @Binds
    abstract fun bindDataStorage(dataStorageImpl: DataStorageImpl): DataStorage
}