package comvannv.train.dashcoin.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import comvannv.train.dashcoin.core.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_NAME)

suspend fun Context.writeBool(key: String = Constants.SAVED_COIN_KEY, value: Boolean) {
    dataStore.edit { pref -> pref[booleanPreferencesKey(key)] = value }
}

fun Context.readBool(key: String = Constants.SAVED_COIN_KEY): Flow<Boolean> = dataStore.data.map { pref -> pref[booleanPreferencesKey(key)] ?: false }