package `in`.rithikjain.spillthetea.data.repository

import `in`.rithikjain.spillthetea.utils.Constants
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class DataStoreRepositoryImpl(private val context: Context) : DataStoreRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATA_STORE_NAME)

    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { prefs ->
            prefs[preferencesKey] = value
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { prefs ->
            prefs[preferencesKey] = value
        }
    }

    override fun getString(key: String): Flow<String?> {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            context.dataStore.data.map { preferences ->
                preferences[preferencesKey]
            }
        } catch (e: Exception) {
            e.printStackTrace()
            flowOf(null)
        }
    }

    override fun getInt(key: String): Flow<Int?> {
        return try {
            val preferencesKey = intPreferencesKey(key)
            context.dataStore.data.map { preferences ->
                preferences[preferencesKey]
            }
        } catch (e: Exception) {
            e.printStackTrace()
            flowOf(null)
        }
    }
}