package com.vannv.train.newsfly.ui.search

import com.vannv.train.newsfly.room.entity.RecentArticle
import com.vannv.train.newsfly.room.main.ArticlesDatabase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:15 PM
 */
@Singleton
class LocalSearchDataSource @Inject constructor(database: ArticlesDatabase) :
    SearchDataSource {
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