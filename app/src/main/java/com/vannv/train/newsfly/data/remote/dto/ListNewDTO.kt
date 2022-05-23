package com.vannv.train.newsfly.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */

data class ListNewDTO(
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("articles")
    val news: List<NewDTO>?
)