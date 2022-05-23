package com.vannv.train.newsfly.domain.usecase

import com.vannv.train.newsfly.data.remote.base.Repo
import com.vannv.train.newsfly.data.remote.base.RequestService
import com.vannv.train.newsfly.domain.entity.New
import com.vannv.train.newsfly.domain.repository.SearchRepository
import com.vannv.train.newsfly.network.RequestState
import com.vannv.train.newsfly.network.UiState
import com.vannv.train.newsfly.utils.JSON
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */

class SearchUseCase(val getNews: GetNews)

class GetNews(private val repository: SearchRepository) {
    operator fun invoke(repo: Repo): Flow<UiState<List<New>>> {
        return repository.getDataList(repo)
    }
}