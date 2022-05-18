package com.vannv.train.newsfly.data.datasource.search

import com.vannv.train.newsfly.domain.entity.RecentArticle

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:15 PM
 */

interface LocalSearchDataSource {
    suspend fun getListSearch(key: String): List<RecentArticle>
    suspend fun clearList()
    suspend fun insertList(list: List<RecentArticle>)
}

interface RemoteSearchDataSource {
    suspend fun getListSearch(key: String): List<RecentArticle>
}