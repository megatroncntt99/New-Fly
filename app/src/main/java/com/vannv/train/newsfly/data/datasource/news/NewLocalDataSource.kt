package com.vannv.train.newsfly.data.datasource.news

import com.vannv.train.newsfly.domain.entity.New
import kotlinx.coroutines.flow.Flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */

interface NewLocalDataSource {
    fun getNews(): Flow<List<New>>

    suspend fun insertNews(news: List<New>)

    suspend fun deleteNews()
}