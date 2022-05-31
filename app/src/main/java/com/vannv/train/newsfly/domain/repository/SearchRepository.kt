package com.vannv.train.newsfly.domain.repository

import com.vannv.train.newsfly.data.remote.base.Repo
import com.vannv.train.newsfly.domain.entity.New
import com.vannv.train.newsfly.presentation.UiState

import kotlinx.coroutines.flow.Flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */

interface SearchRepository {
    fun getDataList(repo: Repo): Flow<UiState<List<New>>>
}