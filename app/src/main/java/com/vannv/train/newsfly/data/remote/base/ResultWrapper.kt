package com.vannv.train.newsfly.data.remote.base

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val error: String, val message: String? = null) : ResultWrapper<Nothing>()
}