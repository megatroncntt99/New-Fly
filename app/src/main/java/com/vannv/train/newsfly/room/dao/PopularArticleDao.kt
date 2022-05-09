package com.vannv.train.newsfly.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vannv.train.newsfly.room.entity.PopularArticle
import kotlinx.coroutines.flow.Flow

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:45 PM
 */
@Dao
interface PopularArticleDao {
    /**
     * Dao Popular article
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePopularArticles(popularArticle: List<PopularArticle>)

    @Query("SELECT * FROM popular_article")
    fun getAllPopularArticles(): Flow<List<PopularArticle>>

    @Query("DELETE FROM popular_article")
    suspend fun deletePopularArticles()
}