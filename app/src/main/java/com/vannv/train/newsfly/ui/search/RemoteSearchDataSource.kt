package com.vannv.train.newsfly.ui.search

import com.vannv.train.newsfly.data.ApiDataSource
import com.vannv.train.newsfly.network.BaseDataSource
import com.vannv.train.newsfly.network.RequestState
import com.vannv.train.newsfly.room.entity.RecentArticle
import com.vannv.train.newsfly.utils.handleStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:16 PM
 */
@Singleton
class RemoteSearchDataSource @Inject constructor(private val apiDataSource: ApiDataSource) :
    SearchDataSource, BaseDataSource() {
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