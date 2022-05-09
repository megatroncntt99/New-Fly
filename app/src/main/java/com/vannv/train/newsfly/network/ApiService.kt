package com.vannv.train.newsfly.network

import com.vannv.train.newsfly.model.remote.RecentNewsResponse
import com.vannv.train.newsfly.model.remote.PopularNewsResponse
import com.vannv.train.newsfly.utils.Urls
import retrofit2.Response
import retrofit2.http.*

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 2:00 PM
 */

interface ApiService {
    @GET("top-headlines?country=us")
    suspend fun getRecentNews(
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int?,
        @Query("apiKey") apiKey: String = Urls.API_KEY,
    ): RecentNewsResponse

    @GET("top-headlines?country=us")
    suspend fun getPopularNews(
        @Query("q") q: String = "",
        @Query("apiKey") apiKey: String = Urls.API_KEY,
        @Query("pageSize") pageSize: Int = 5
    ): Response<PopularNewsResponse>

    @GET("everything")
    suspend fun searchForNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = Urls.API_KEY
    ): Response<RecentNewsResponse>

    @GET
    suspend fun <T> get(
        @Url url: String,
        @HeaderMap headerMap: Map<String, String>,
        @QueryMap queryMap: Map<String, String>,
    ): T
}