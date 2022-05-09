package com.vannv.train.newsfly.network

/**
 * Creator: Nguyen Van Van
 * Date: 05,May,2022
 * Time: 3:14 PM
 */

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val error: String, val message: String? = null,val code: Int? = null) :
        ResultWrapper<Nothing>()
}