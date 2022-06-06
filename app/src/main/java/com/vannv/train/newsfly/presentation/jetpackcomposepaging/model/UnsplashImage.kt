package com.vannv.train.newsfly.presentation.jetpackcomposepaging.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.vannv.train.newsfly.utils.Constant.UNSPLASH_IMAGE_TABLE
import com.vannv.train.newsfly.utils.Constant.UNSPLASH_REMOTE_KEYS_TABLE

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */

@Entity(tableName = UNSPLASH_IMAGE_TABLE)
data class UnsplashImage(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @Embedded
    val urls: Urls,
    val likes: Int,
    @Embedded
    val user: User
)

data class Urls(
    val regular: String
)

data class User(
    @SerializedName("links")
    @Embedded
    val userLinks: UserLinks,
    val username: String
)

data class UserLinks(
    val html: String
)
data class SearchResult(
    @SerializedName("results")
    val images: List<UnsplashImage>,
)

@Entity(tableName = UNSPLASH_REMOTE_KEYS_TABLE)
data class UnsplashRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)