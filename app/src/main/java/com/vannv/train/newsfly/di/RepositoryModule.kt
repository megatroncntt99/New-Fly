package com.vannv.train.newsfly.di

import com.vannv.train.newsfly.data.datasource.news.NewLocalDataSource
import com.vannv.train.newsfly.data.datasource.news.NewLocalDataSourceImpl
import com.vannv.train.newsfly.data.datasource.news.NewRemoteDataSource
import com.vannv.train.newsfly.data.datasource.news.NewRemoteDataSourceImpl
import com.vannv.train.newsfly.data.repository.SearchRepositoryImpl
import com.vannv.train.newsfly.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNewLocalDataSource(newLocalDataSourceImpl: NewLocalDataSourceImpl): NewLocalDataSource

    @Binds
    abstract fun bindNewRemoteDataSource(newRemoteDataSourceImpl: NewRemoteDataSourceImpl): NewRemoteDataSource

    @Binds
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}