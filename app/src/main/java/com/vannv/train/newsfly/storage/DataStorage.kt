package com.vannv.train.newsfly.storage

import kotlinx.coroutines.flow.Flow

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 2:20 PM
 */

interface DataStorage {
    fun selectedTheme(): Flow<String>
    suspend fun setSelectedTheme(theme: String)
}