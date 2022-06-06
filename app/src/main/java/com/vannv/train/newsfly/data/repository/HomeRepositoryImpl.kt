package com.vannv.train.newsfly.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vannv.train.newsfly.data.local.room.unplash.UnsplashDatabase
import com.vannv.train.newsfly.data.local.room.unplash.UnsplashImageDao
import com.vannv.train.newsfly.data.local.room.unplash.UnsplashRemoteKeysDao
import com.vannv.train.newsfly.data.paging.SearchPagingSource
import com.vannv.train.newsfly.data.paging.UnsplashRemoteMediator
import com.vannv.train.newsfly.data.remote.base.RequestService
import com.vannv.train.newsfly.data.remote.repo.RepoManager
import com.vannv.train.newsfly.domain.repository.HomeRepository
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashImage
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.home.HomeRepo
import com.vannv.train.newsfly.utils.Constant
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
@OptIn(ExperimentalPagingApi::class)
class HomeRepositoryImpl @Inject constructor(
    private val requestService: RequestService,
    private val unsplashDatabase: UnsplashDatabase,
) : HomeRepository {
    protected val repo = RepoManager() as HomeRepo
    override fun getAllImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = { unsplashDatabase.unsplashImageDao().getAllImage() }
        return Pager(
            config = PagingConfig(pageSize = Constant.ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(requestService, unsplashDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    override fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(config = PagingConfig(pageSize = Constant.ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(requestService, query)
            }).flow
    }
}