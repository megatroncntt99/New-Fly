package com.vannv.train.newsfly.di

import com.vannv.train.newsfly.data.local.storage.DataStorage
import com.vannv.train.newsfly.data.local.storage.DataStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 2:31 PM
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Binds
    abstract fun bindDataStorage(dataStorageImpl: DataStorageImpl): DataStorage
}