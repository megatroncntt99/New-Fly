package com.vannv.train.newsfly.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vannv.train.newsfly.data.local.room.unplash.UnsplashDatabase
import com.vannv.train.newsfly.data.local.room.unplash.UnsplashImageDao
import com.vannv.train.newsfly.data.local.room.unplash.UnsplashRemoteKeysDao
import com.vannv.train.newsfly.data.remote.base.Repo
import com.vannv.train.newsfly.data.remote.base.RequestService
import com.vannv.train.newsfly.data.remote.dto.ListNewDTO
import com.vannv.train.newsfly.data.remote.repo.BaseRepo
import com.vannv.train.newsfly.data.remote.repo.RepoManager
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashImage
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashRemoteKeys
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.home.HomeRepo
import com.vannv.train.newsfly.utils.JSON
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
@OptIn(ExperimentalPagingApi::class)
class UnsplashRemoteMediator(
    private val requestService: RequestService,
    private val unsplashDatabase: UnsplashDatabase,

    ) : RemoteMediator<Int, UnsplashImage>() {
    private val unsplashImageDao: UnsplashImageDao = unsplashDatabase.unsplashImageDao()
    private val unsplashRemoteKeysDao: UnsplashRemoteKeysDao =
        unsplashDatabase.unsplashRemoteKeyDao()
    private val repo = RepoManager() as HomeRepo
    private var mediatorResult: MediatorResult = MediatorResult.Error(Throwable())

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImage>
    ): MediatorResult {
        try {
            val currentPage: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKetForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            requestService.work(
                onSuccess = {
                    val response =
                        JSON.decodeToList(it.value.data(), Array<UnsplashImage>::class.java)
                    val endOfPaginationReached = response.isEmpty()
                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached) null else currentPage + 1
                    unsplashDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            unsplashImageDao.deleteAllImages()
                            unsplashRemoteKeysDao.deleteAllRemoteKeys()
                        }
                        val keys =
                            response.map { unsplashImage ->
                                UnsplashRemoteKeys(
                                    id = unsplashImage.id,
                                    prevPage,
                                    nextPage
                                )
                            }
                        unsplashRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                        unsplashImageDao.addImages(images = response)
                    }
                    mediatorResult = MediatorResult.Success(endOfPaginationReached)
                },
                onError = {
                    mediatorResult = MediatorResult.Error(Throwable(message = it.message))
                }).request(repo.repoGetAllImages(page = currentPage))

            return mediatorResult

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKetForLastItem(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            unsplashRemoteKeysDao.getRemoteKeys(id = it.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { item ->
            unsplashRemoteKeysDao.getRemoteKeys(id = item.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UnsplashImage>): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id)
            }
        }
    }
}