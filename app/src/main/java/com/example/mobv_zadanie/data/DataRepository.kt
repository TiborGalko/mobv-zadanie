package com.example.mobv_zadanie.data

import androidx.lifecycle.LiveData
import com.example.mobv_zadanie.data.db.LocalCache
import com.example.mobv_zadanie.data.db.model.UserItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem

class DataRepository private constructor(private val cache: LocalCache) {

    companion object {
        const val TAG = "DataRepository"
        @Volatile
        private var INSTANCE: DataRepository ?= null

        fun getInstance(cache: LocalCache): DataRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataRepository(cache).also { INSTANCE = it }
            }
    }

    fun getUsers() : LiveData<List<UserItem>> = cache.getUsers()
    fun getWifiRooms() : LiveData<List<WifiRoomItem>> = cache.getWifiRooms()
    suspend fun insertWifiRoom(wifiRoomItem: WifiRoomItem) = cache.insertWifiRoom(wifiRoomItem)
}