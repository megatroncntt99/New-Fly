package com.vannv.train.newsfly.domain.repository

import androidx.paging.PagingData
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
interface HomeRepository {
    fun getAllImages(): Flow<PagingData<UnsplashImage>>
    fun searchImages(query: String): Flow<PagingData<UnsplashImage>>
}