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


    fun getWifiRooms() : LiveData<List<WifiRoomItem>> = cache.getWifiRooms()
    fun getWifiRoomsSorted() : LiveData<List<WifiRoomItem>> = cache.getWifiRoomsSorted()
    suspend fun insertWifiRoom(wifiRoomItem: WifiRoomItem) = cache.insertWifiRoom(wifiRoomItem)
    suspend fun updateWifiRoom(wifiRoomItem: WifiRoomItem) = cache.updateWifiRoom(wifiRoomItem)

    fun getUsers() : LiveData<List<UserItem>> = cache.getUsers()
    suspend fun insertUser(userItem: UserItem) = cache.insertUser(userItem)

}