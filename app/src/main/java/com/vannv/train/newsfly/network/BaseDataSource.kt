package com.vannv.train.newsfly.network

import com.google.gson.Gson
import com.vannv.train.newsfly.model.remote.ErrorResponse
import retrofit2.Response

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 2:13 PM
 */

abstract class BaseDataSource {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): UiState<T> {
        try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return UiState(RequestState.SUCCESS, result = body)
                }
            } else {
                val message: ErrorResponse =
                    Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
                return UiState(
                    RequestState.ERROR,
                    message = message.message,
                    code = message.code.toInt()
                )
            }
            return UiState(
                RequestState.ERROR,
                message = "Something went wrong, try again later",
                code = 0
            )

        } catch (e: Exception) {
            e.printStackTrace()
            return UiState(RequestState.ERROR, message = e.message, code = e.hashCode())

        }
    }
}