package com.vannv.train.newsfly.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SourceDTO(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)