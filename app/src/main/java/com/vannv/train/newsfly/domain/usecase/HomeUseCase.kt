package com.vannv.train.newsfly.domain.usecase

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Query
import com.vannv.train.newsfly.data.remote.base.Repo
import com.vannv.train.newsfly.domain.entity.New
import com.vannv.train.newsfly.domain.repository.HomeRepository
import com.vannv.train.newsfly.domain.repository.SearchRepository
import com.vannv.train.newsfly.presentation.UiState
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
class HomeUseCase(val getAllImages: GetAllImages, val getSearchImages: GetSearchImages)

class GetAllImages(private val repository: HomeRepository) {
    operator fun invoke(): Flow<PagingData<UnsplashImage>> {
        return repository.getAllImages()
    }
}

class GetSearchImages(private val repository: HomeRepository) {
    operator fun invoke(query: String): Flow<PagingData<UnsplashImage>> {
        return repository.searchImages(query)
    }
}