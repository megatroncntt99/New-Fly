package com.vannv.train.newsfly.ui.search

import com.vannv.train.newsfly.network.UiState
import com.vannv.train.newsfly.room.entity.RecentArticle
import kotlinx.coroutines.flow.Flow

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:17 PM
 */

interface SearchRepository {
    suspend fun getListSearch(key:String):Flow<UiState<List<RecentArticle>>>
}