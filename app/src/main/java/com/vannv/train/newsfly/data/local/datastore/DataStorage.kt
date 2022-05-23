package com.vannv.train.newsfly.data.local.datastore

import kotlinx.coroutines.flow.Flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */

interface DataStorage {
    fun selectedTheme(): Flow<String>
    suspend fun setSelectedTheme(theme: String)
}