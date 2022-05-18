package com.vannv.train.newsfly.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vannv.train.newsfly.domain.entity.PopularArticle

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:58 PM
 */

data class PopularNewsDTO(
    @SerializedName("articles")
    val popularArticles: List<PopularArticle>,
    val status: String,
    val totalResults: Int
)