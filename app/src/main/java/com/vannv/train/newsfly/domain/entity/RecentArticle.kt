package com.vannv.train.newsfly.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:37 PM
 */
@Entity(tableName = "recent_article")
data class RecentArticle(
    @PrimaryKey
    val url: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val urlToImage: String?
) : Serializable