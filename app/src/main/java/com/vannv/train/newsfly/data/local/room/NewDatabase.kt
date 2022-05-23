package com.vannv.train.newsfly.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vannv.train.newsfly.domain.entity.New

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */
@Database(entities = [New::class], version = 1)
abstract class NewDatabase : RoomDatabase() {
    abstract fun newDao(): NewDao
}