package com.vannv.train.newsfly.model.remote

import com.google.gson.annotations.SerializedName
import com.vannv.train.newsfly.room.entity.PopularArticle

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:58 PM
 */

data class PopularNewsResponse(
    @SerializedName("articles")
    val popularArticles: List<PopularArticle>,
    val status: String,
    val totalResults: Int
)