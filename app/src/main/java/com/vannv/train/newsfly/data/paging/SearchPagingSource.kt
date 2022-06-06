package com.vannv.train.newsfly.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vannv.train.newsfly.data.remote.base.RequestService
import com.vannv.train.newsfly.data.remote.repo.RepoManager
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.SearchResult
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashImage
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.home.HomeRepo
import com.vannv.train.newsfly.utils.JSON

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
class SearchPagingSource(private val requestService: RequestService, private val query: String) :
    PagingSource<Int, UnsplashImage>() {
    private val repo = RepoManager() as HomeRepo
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val currentPage = params.key ?: 1
        var loadResult: LoadResult<Int, UnsplashImage>? = null
        return try {
            requestService.work(
                onSuccess = {
                    val response =
                        JSON.decode(it.value.data(), SearchResult::class.java)
                    response ?: return@work
                    val endOfPaginationReached = response.images.isEmpty()
                    loadResult = if (response.images.isNotEmpty()) {
                        LoadResult.Page(
                            data = response.images,
                            prevKey = if (currentPage == 1) null else currentPage - 1,
                            nextKey = if (endOfPaginationReached) null else currentPage + 1
                        )
                    } else {
                        LoadResult.Page(
                            data = emptyList(),
                            prevKey = null,
                            nextKey = null
                        )
                    }
                },
                onError = {
                    loadResult = LoadResult.Error(Throwable(message = it.message))
                }
            ).request(repo.repoGetSearchImages(query))

            loadResult ?: LoadResult.Error(Throwable(message = "Lỗi Không Xác Định"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        return state.anchorPosition
    }
}