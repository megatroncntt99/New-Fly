package com.vannv.train.newsfly.data.remote.base

import androidx.room.Delete
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */

interface ApiService {
    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap params:  Map<String, String>? = null,
        @HeaderMap headers: Map<String, String>? = null
    ): Response<JsonElement>

    @POST
    suspend fun post(
        @Url url: String,
        @Body body: Any? = null,
        @HeaderMap headers: Map<String, String>? = null
    ): Response<JsonElement>

    @PUT
    suspend fun put(
        @Url url: String,
        @Body body: Any? = null,
        @HeaderMap headers: Map<String, String>? = null
    ): Response<JsonElement>

    @Delete
    suspend fun delete(
        @Url url: String,
        @QueryMap params: Map<String, String>? = null,
        @HeaderMap headers: Map<String, String>? = null
    ): Response<JsonElement>
}