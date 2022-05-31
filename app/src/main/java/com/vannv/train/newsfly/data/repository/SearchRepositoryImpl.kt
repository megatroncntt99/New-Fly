package com.vannv.train.newsfly.data.repository


import com.vannv.train.newsfly.data.datasource.news.NewLocalDataSource
import com.vannv.train.newsfly.data.datasource.news.NewRemoteDataSource
import com.vannv.train.newsfly.data.remote.base.Repo
import com.vannv.train.newsfly.domain.entity.New
import com.vannv.train.newsfly.domain.repository.SearchRepository
import com.vannv.train.newsfly.presentation.UiState

import com.vannv.train.newsfly.utils.LogCat
import com.vannv.train.newsfly.utils.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */
@ExperimentalCoroutinesApi
class SearchRepositoryImpl @Inject constructor(
    private val newLocalDataSource: NewLocalDataSource,
    private val newRemoteDataSource: NewRemoteDataSource
) : SearchRepository {
    override fun getDataList(repo: Repo): Flow<UiState<List<New>>> {
        return networkBoundResource(
            query = {
                newLocalDataSource.getNews()
            },
            fetch = {
                newRemoteDataSource.getNews(repo)
            },
            saveFetchResult = {
                newLocalDataSource.deleteNews()
                newLocalDataSource.insertNews(it)
            },
        )
    }
}