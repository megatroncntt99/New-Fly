package com.vannv.train.newsfly.ui.search

import com.vannv.train.newsfly.model.remote.RecentNewsResponse
import com.vannv.train.newsfly.room.entity.RecentArticle
import kotlinx.coroutines.flow.Flow

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:15 PM
 */

interface SearchDataSource {
    suspend fun getListSearch(key: String): List<RecentArticle>
    suspend fun clearList(){}
    suspend fun insertList(list: List<RecentArticle>){}
}