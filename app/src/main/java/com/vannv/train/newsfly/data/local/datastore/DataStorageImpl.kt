package com.vannv.train.newsfly.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.utils.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */
@Singleton
class DataStorageImpl @Inject constructor(@ApplicationContext val context: Context) : DataStorage {

    private val dataStore = context.dataStore

    private object PreferenceKeys {
        val SELECT_THEME = stringPreferencesKey("select_theme")
    }

    override fun selectedTheme(): Flow<String> =
        dataStore.data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else throw it
        }.map {
            it[PreferenceKeys.SELECT_THEME] ?: context.getString(R.string.light_mode)
        }

    override suspend fun setSelectedTheme(theme: String) {
        dataStore.edit {
            it[PreferenceKeys.SELECT_THEME] = theme
        }
    }
}