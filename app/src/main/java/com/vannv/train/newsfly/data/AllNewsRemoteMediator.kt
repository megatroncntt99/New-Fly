package com.vannv.train.newsfly.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vannv.train.newsfly.room.dao.AllNewsRemoteKeyDao
import com.vannv.train.newsfly.room.dao.PopularArticleDao
import com.vannv.train.newsfly.room.dao.RecentArticleDao
import com.vannv.train.newsfly.room.entity.AllNewsRemoteKey
import com.vannv.train.newsfly.room.entity.RecentArticle
import com.vannv.train.newsfly.room.main.ArticlesDatabase
import com.vannv.train.newsfly.utils.Constant.NEWS_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

/**
 * Creator: Nguyen Van Van
 * Date: 21,April,2022
 * Time: 9:38 AM
 */

@OptIn(ExperimentalPagingApi::class)
class AllNewsRemoteMediator constructor(
    private val apiDataSource: ApiDataSource,
    private val newsArticleDb: ArticlesDatabase,
) : RemoteMediator<Int, RecentArticle>() {

    private val recentArticleDao = newsArticleDb.recentArticleDao()
    private val remoteKeysDao = newsArticleDb.newsRemoteKeyDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecentArticle>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> NEWS_STARTING_PAGE_INDEX
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> remoteKeysDao.getRemoteKeys().nextPageKey
            }
            val response = apiDataSource.getRecentNews(
                page = loadKey,
                pageSize = state.config.pageSize
            )
            val listing = response.recentArticles
            val articles = listing.map { it }

            val nextPageKey = loadKey + 1
            val prevPageKey = loadKey - 1

            newsArticleDb.withTransaction {
                remoteKeysDao.saveRemoteKeys(
                    AllNewsRemoteKey(
                        0,
                        nextPageKey = nextPageKey,
                        prevPageKey = prevPageKey
                    )
                )
                recentArticleDao.saveRecentArticles(articles)
            }

            MediatorResult.Success(endOfPaginationReached = listing.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}