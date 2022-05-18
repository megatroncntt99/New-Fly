package com.vannv.train.newsfly.data.datasource.search

import com.vannv.train.newsfly.domain.entity.RecentArticle
import com.vannv.train.newsfly.data.local.room.main.ArticlesDatabase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:15 PM
 */

class LocalSearchDataSourceImpl @Inject constructor(database: ArticlesDatabase) :
    LocalSearchDataSource {
    private val recentArticleDao = database.recentArticleDao()
    override suspend fun getListSearch(key: String): List<RecentArticle> {
        println("Local Data Source")
        return recentArticleDao.getSearchRecentArticles(key)
    }

    override suspend fun insertList(list: List<RecentArticle>) {
        recentArticleDao.saveRecentArticles(list)
    }

    override suspend fun clearList() {
        recentArticleDao.deleteRecentArticles()
    }
}