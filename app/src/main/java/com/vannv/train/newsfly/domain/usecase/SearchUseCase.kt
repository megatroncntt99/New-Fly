package com.vannv.train.newsfly.domain.usecase

import com.vannv.train.newsfly.data.remote.ResultWrapper
import com.vannv.train.newsfly.domain.entity.RecentArticle
import com.vannv.train.newsfly.domain.repository.SearchRepository
import com.vannv.train.newsfly.network.RequestState
import com.vannv.train.newsfly.network.UiState
import kotlinx.coroutines.flow.*

/**
 * Author: vannv8@fpt.com.vn
 * Date: 18/05/2022
 */

class SearchUseCase(
    val getListData: GetListData
)

class GetListData(private val repository: SearchRepository) {
    suspend operator fun invoke(key: String) = flow<UiState<List<RecentArticle>>> {

        repository.getListSearch(key).catch {
            emit(UiState(RequestState.ERROR, message = it.message, code = it.hashCode()))
        }.collect {
            when (it) {
                is ResultWrapper.Success -> emit(UiState(RequestState.SUCCESS, result = it.value))
                is ResultWrapper.Error -> {
                    emit(UiState(RequestState.ERROR, message = it.error, code = it.code))
                }
            }
        }
    }


}