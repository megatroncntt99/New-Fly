package com.vannv.train.newsfly.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vannv.train.newsfly.domain.entity.RecentArticle

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:57 PM
 */

data class RecentNewsDTO(
 @SerializedName("articles")
 val recentArticles: List<RecentArticle>,
 val status: String,
 val totalResults: Int
)
