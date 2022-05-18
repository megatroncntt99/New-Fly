package com.vannv.train.newsfly.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:31 PM
 */
@Entity(tableName = "all_news_remote_key")
data class AllNewsRemoteKey(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nextPageKey: Int,
    val prevPageKey: Int
)
