package com.vannv.train.newsfly.data.datasource.search

import com.vannv.train.newsfly.data.ApiDataSource
import com.vannv.train.newsfly.data.remote.BaseDataSource
import com.vannv.train.newsfly.network.RequestState
import com.vannv.train.newsfly.domain.entity.RecentArticle
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:16 PM
 */
@Singleton
class RemoteSearchDataSourceImpl @Inject constructor(private val apiDataSource: ApiDataSource) :
    RemoteSearchDataSource, BaseDataSource() {
    override suspend fun getListSearch(key: String): List<RecentArticle> {
        println("Remote Data Source")
        val result = safeApiCall { apiDataSource.searchForNews(key) }
        return when (result.state) {
            RequestState.SUCCESS -> {
                result.result?.recentArticles ?: emptyList()
            }
            else -> {
                emptyList()
            }
        }
    }
}