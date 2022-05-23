package com.vannv.train.newsfly.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vannv.train.newsfly.domain.entity.New
import kotlinx.coroutines.flow.Flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */
@Dao
interface NewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<New>)

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<New>>

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()
}