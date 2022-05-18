package com.vannv.train.newsfly.domain.repository

import com.vannv.train.newsfly.data.remote.ResultWrapper
import com.vannv.train.newsfly.network.UiState
import com.vannv.train.newsfly.domain.entity.RecentArticle
import kotlinx.coroutines.flow.Flow

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:17 PM
 */

interface SearchRepository {
    suspend fun getListSearch(key:String):Flow<ResultWrapper<List<RecentArticle>>>
}