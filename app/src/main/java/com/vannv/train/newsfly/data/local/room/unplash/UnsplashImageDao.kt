package com.vannv.train.newsfly.data.local.room.unplash

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashImage

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
@Dao
interface UnsplashImageDao {
    @Query("SELECT * FROM unsplash_image_table")
    fun getAllImage(): PagingSource<Int, UnsplashImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<UnsplashImage>)

    @Query("DELETE FROM unsplash_image_table")
    suspend fun deleteAllImages()
}