package com.vannv.train.newsfly.di

import com.vannv.train.newsfly.data.datasource.search.*
import com.vannv.train.newsfly.data.repository.SearchRepositoryImpl
import com.vannv.train.newsfly.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:22 PM
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Local
    @Binds
    abstract fun bindLocalSearchDataSource(localSearchDataSource: LocalSearchDataSourceImpl): LocalSearchDataSource

    @Remote
    @Binds
    abstract fun bindRemoteSearchDataSource(remoteSearchDataSource: RemoteSearchDataSourceImpl): RemoteSearchDataSource

    @Binds
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Local

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Remote