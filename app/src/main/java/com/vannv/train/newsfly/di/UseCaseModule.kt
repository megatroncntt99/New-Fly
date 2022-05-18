package com.vannv.train.newsfly.di

import com.vannv.train.newsfly.domain.usecase.GetListData
import com.vannv.train.newsfly.domain.usecase.SearchUseCase
import com.vannv.train.newsfly.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Author: vannv8@fpt.com.vn
 * Date: 18/05/2022
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @ViewModelScoped
    @Provides
    fun provideSearchUseCase(searchRepository: SearchRepository) =
        SearchUseCase(getListData = GetListData(searchRepository))
}