package com.vannv.train.newsfly.presentation

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 2:08 PM
 */

data class UiState<T>(
    val state: RequestState = RequestState.NON,
    val result: T? = null,
    val message: String? = null,
    val code: Int? = null
)

enum class RequestState(val state: Int) {
    NON(-1), SUCCESS(1), ERROR(0);

    companion object {
        fun find(state: Int): RequestState {
            values().forEach {
                if (it.state == state)
                    return it
            }
            return NON
        }
    }
}