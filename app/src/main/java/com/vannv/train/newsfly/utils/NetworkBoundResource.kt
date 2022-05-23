package com.vannv.train.newsfly.utils

import com.vannv.train.newsfly.network.RequestState
import com.vannv.train.newsfly.network.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Creator: Nguyen Van Van
 * Date: 21,April,2022
 * Time: 9:51 AM
 */

@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> Flow<UiState<RequestType>>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline onFetchSuccess: () -> Unit = { },
    crossinline onFetchFailed: (Throwable) -> Unit = { }
): Flow<UiState<ResultType>> = channelFlow {
    val data = query().first()
    if (shouldFetch(data)) {
        val loading = launch {
            query().collect { send(UiState(RequestState.NON)) }
        }
        try {
            fetch().collect {
                when (it.state) {
                    RequestState.SUCCESS -> {
                        it.result ?: return@collect
                        saveFetchResult(it.result)
                    }
                    RequestState.ERROR -> {
                        send(
                            UiState(RequestState.ERROR, message = it.message, code = it.code)
                        )
                    }
                }

            }
            onFetchSuccess()
            loading.cancel()
            query().collect { send(UiState(RequestState.SUCCESS, result = it)) }
        } catch (t: Throwable) {
            onFetchFailed(t)
            loading.cancel()
            query().collect {
                send(
                    UiState(
                        RequestState.ERROR,
                        message = t.message,
                        code = t.hashCode()
                    )
                )
            }
        }
    } else {
        query().collect { send(UiState(RequestState.SUCCESS, result = it)) }
    }
}