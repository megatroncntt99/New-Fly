package com.vannv.train.newsfly.data.datasource.news

import com.vannv.train.newsfly.data.mapper.mapToNew
import com.vannv.train.newsfly.data.remote.base.Repo
import com.vannv.train.newsfly.data.remote.base.RequestService
import com.vannv.train.newsfly.data.remote.dto.ListNewDTO
import com.vannv.train.newsfly.domain.entity.New
import com.vannv.train.newsfly.network.RequestState
import com.vannv.train.newsfly.network.UiState
import com.vannv.train.newsfly.utils.JSON
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */

class NewRemoteDataSourceImpl @Inject constructor(private val request: RequestService) :
    NewRemoteDataSource {
    override suspend fun getNews(repo: Repo) = flow<UiState<List<New>>> {
        request.work(
            onSuccess = {
                val news = JSON.decode(it.value.data(), ListNewDTO::class.java)
                emit(
                    UiState(
                        RequestState.SUCCESS,
                        result = news?.news?.map { new -> new.mapToNew() }
                    )
                )
            },
            onError = {
                emit(UiState(RequestState.ERROR, message = it.message))
            }
        ).request(repo)
    }
}