package com.vannv.train.newsfly.data.remote

import com.vannv.train.newsfly.data.remote.dto.RecentNewsDTO
import com.vannv.train.newsfly.data.remote.dto.PopularNewsDTO
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
    ): RecentNewsDTO

    @GET("top-headlines?country=us")
    suspend fun getPopularNews(
        @Query("q") q: String = "",
        @Query("apiKey") apiKey: String = Urls.API_KEY,
        @Query("pageSize") pageSize: Int = 5
    ): Response<PopularNewsDTO>

    @GET("everything")
    suspend fun searchForNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = Urls.API_KEY
    ): Response<RecentNewsDTO>

    @GET
    suspend fun <T> get(
        @Url url: String,
        @HeaderMap headerMap: Map<String, String>,
        @QueryMap queryMap: Map<String, String>,
    ): T
}