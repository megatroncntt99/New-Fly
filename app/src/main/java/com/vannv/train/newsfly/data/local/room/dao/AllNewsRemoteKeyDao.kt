package com.vannv.train.newsfly.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vannv.train.newsfly.domain.entity.AllNewsRemoteKey

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:46 PM
 */
@Dao
interface AllNewsRemoteKeyDao {
    /**
     * Dao All news remote key
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRemoteKeys(remoteKey: AllNewsRemoteKey)

    @Query("SELECT * FROM all_news_remote_key ORDER BY id DESC")
    suspend fun getRemoteKeys(): AllNewsRemoteKey

    @Query("DELETE FROM all_news_remote_key")
    suspend fun deleteRemoteKeys()
}