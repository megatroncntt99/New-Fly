package com.vannv.train.newsfly.data.datasource.news

import com.vannv.train.newsfly.data.remote.base.Repo
import com.vannv.train.newsfly.data.remote.dto.NewDTO
import com.vannv.train.newsfly.domain.entity.New
import com.vannv.train.newsfly.presentation.UiState

import kotlinx.coroutines.flow.Flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */

interface NewRemoteDataSource {
 suspend fun getNews(repo: Repo): Flow<UiState<List<New>>>
}