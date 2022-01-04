package `in`.rithikjain.spillthetea.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    fun getString(key: String): Flow<String?>
    fun getInt(key: String): Flow<Int?>
}