package com.vannv.train.newsfly.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vannv.train.newsfly.utils.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 2:21 PM
 */
@Singleton
class DataStorageImpl @Inject constructor(@ApplicationContext context: Context) :
    DataStorage {

    private val dataStore = context.dataStore

    private object PreferenceKeys {
        val SELECTED_THEME = stringPreferencesKey("selected_theme")
    }

    override fun selectedTheme(): Flow<String> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[PreferenceKeys.SELECTED_THEME] ?: "light"
    }

    override suspend fun setSelectedTheme(theme: String) {
        dataStore.edit {
            it[PreferenceKeys.SELECTED_THEME] = theme
        }
    }
}