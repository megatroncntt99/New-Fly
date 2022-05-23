package com.vannv.train.newsfly.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */
@Entity("news")
data class New(
    @PrimaryKey
    val url: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    val urlToImage: String
)
