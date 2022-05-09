package com.vannv.train.newsfly.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vannv.train.newsfly.model.Source
import java.io.Serializable

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:33 PM
 */
@Entity(tableName = "popular_article")
data class PopularArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val urlToImage: String?
) : Serializable
