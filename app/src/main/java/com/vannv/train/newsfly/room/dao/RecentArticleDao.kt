package com.vannv.train.newsfly.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vannv.train.newsfly.room.entity.RecentArticle

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:40 PM
 */
@Dao
interface RecentArticleDao {
    /**
     * Dao Recent article
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecentArticles(listArticle: List<RecentArticle>)

    @Query("SELECT * FROM recent_article")
    fun getAllRecentArticles(): PagingSource<Int, RecentArticle>

    @Query("DELETE FROM recent_article")
    suspend fun deleteRecentArticles()

    @Query("SELECT * FROM recent_article WHERE content LIKE '%' || :key || '%'")
    suspend fun getSearchRecentArticles(key: String): List<RecentArticle>
}