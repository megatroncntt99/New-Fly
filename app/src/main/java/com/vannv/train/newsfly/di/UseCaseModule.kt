package com.vannv.train.newsfly.di

import com.vannv.train.newsfly.domain.repository.HomeRepository
import com.vannv.train.newsfly.domain.repository.SearchRepository
import com.vannv.train.newsfly.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideSearchUseCase(searchRepository: SearchRepository) =
        SearchUseCase(getNews = GetNews(searchRepository))

    @Provides
    fun provideHomeUseCase(homeRepository: HomeRepository) = HomeUseCase(
        getAllImages = GetAllImages(homeRepository),
        getSearchImages = GetSearchImages(homeRepository)
    )
}