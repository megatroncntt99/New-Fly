package com.vannv.train.newsfly.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.vannv.train.newsfly.model.remote.RecentNewsResponse
import com.vannv.train.newsfly.network.BaseDataSource
import com.vannv.train.newsfly.network.UiState
import com.vannv.train.newsfly.room.dao.PopularArticleDao
import com.vannv.train.newsfly.room.dao.RecentArticleDao
import com.vannv.train.newsfly.room.entity.PopularArticle
import com.vannv.train.newsfly.room.entity.RecentArticle
import com.vannv.train.newsfly.room.main.ArticlesDatabase
import com.vannv.train.newsfly.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Creator: Nguyen Van Van
 * Date: 21,April,2022
 * Time: 9:33 AM
 */

class AllNewsRepository @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val articlesDatabase: ArticlesDatabase,
    ) : BaseDataSource() {
    private val popularArticleDao = articlesDatabase.popularArticleDao()
    private val recentArticleDao = articlesDatabase.recentArticleDao()

    @OptIn(ExperimentalPagingApi::class)
    fun getRecentNews(): Flow<PagingData<RecentArticle>> = Pager(
        PagingConfig(pageSize = 10, maxSize = 20, prefetchDistance = 2, enablePlaceholders = false),
        remoteMediator = AllNewsRemoteMediator(
            apiDataSource = apiDataSource,
            newsArticleDb = articlesDatabase
        ),
        pagingSourceFactory = {
            recentArticleDao.getAllRecentArticles()
        }
    ).flow

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPopularNews(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<UiState<List<PopularArticle>>> =
        networkBoundResource(
            query = {
                popularArticleDao.getAllPopularArticles()
            },
            fetch = {
                val response = apiDataSource.getPopularNews()
                response.body()?.popularArticles
            },
            saveFetchResult = {
                val popularNewsArticles = it?.map { it }
                articlesDatabase.withTransaction {
                    popularArticleDao.deletePopularArticles()
                    if (popularNewsArticles != null) {
                        popularArticleDao.savePopularArticles(popularNewsArticles)
                    }
                }
            },
            shouldFetch = {
                if (forceRefresh)
                    true
                else {
                    val sortedArticles = it.sortedBy { article ->
                        article.publishedAt
                    }
                    val oldestTimestamp = sortedArticles.firstOrNull()?.id
                    val needsRefresh = oldestTimestamp == null ||
                            oldestTimestamp < System.currentTimeMillis() -
                            TimeUnit.MINUTES.toMillis(60)
                    needsRefresh
                }
            },
            onFetchSuccess = onFetchSuccess,
            onFetchFailed = { t ->
                if (t !is HttpException && t !is IOException) {
                    throw t
                }
                onFetchFailed(t)
            }
        )

    suspend fun searchForNewsItem(q: String) : Flow<UiState<RecentNewsResponse>> {
        return flow {
            val result = safeApiCall { apiDataSource.searchForNews(q) }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}