package com.example.mobv_zadanie.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.mobv_zadanie.data.db.LocalCache
import com.example.mobv_zadanie.data.db.model.ContactItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.data.webapi.CallAPI
import com.example.mobv_zadanie.data.webapi.ListAPI
import com.example.mobv_zadanie.data.webapi.model.ContactListRequest
import com.example.mobv_zadanie.data.webapi.model.RoomListRequest
import java.net.ConnectException

class DataRepository private constructor(private val cache: LocalCache, private val api: ListAPI) {

    companion object {
        const val TAG = "DataRepository"
        @Volatile
        private var INSTANCE: DataRepository ?= null

        fun getInstance(cache: LocalCache, api: ListAPI): DataRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataRepository(cache, api).also { INSTANCE = it }
            }
    }


    fun getWifiRooms() : LiveData<List<WifiRoomItem>> = cache.getWifiRooms()
    fun getWifiRoomsSorted() : LiveData<List<WifiRoomItem>> = cache.getWifiRoomsSorted()
    suspend fun insertWifiRoom(wifiRoomItem: WifiRoomItem) = cache.insertWifiRoom(wifiRoomItem)
    suspend fun updateWifiRoom(wifiRoomItem: WifiRoomItem) = cache.updateWifiRoom(wifiRoomItem)

    fun getContacts() : LiveData<List<ContactItem>> = cache.getContacts()

    // Loading rooms from api and saving to database which triggers LiveData and all that stuff
    suspend fun wifiRoomList(context: Context) {
        CallAPI.setAuthentication(true)
        try {
            val uid: String = SharedPrefWorker.getString(context, "uid", "not_found").toString()
            if (uid == "not_found") {
                return
            }
            val response = api.roomList(RoomListRequest(uid, CallAPI.api_key))
            if (response.isSuccessful) {
                response.body()?.let {
                    return cache.insertWifiRooms(it.map { item -> WifiRoomItem(item.roomid, item.time) })
                }
            }
        } catch (ex: ConnectException) {
            ex.printStackTrace()
            return
        } catch (ex: Exception) {
            ex.printStackTrace()
            return
        }

    }

    // Loading contacts from api and saving to database which triggers LiveData and all that stuff
    suspend fun contactList(context: Context) {
        CallAPI.setAuthentication(true)
        try {
            val uid: String = SharedPrefWorker.getString(context, "uid", "not_found").toString()
            if (uid == "not_found") {
                return
            }
            val response = api.contactList(ContactListRequest(uid, CallAPI.api_key))
            if (response.isSuccessful) {
                response.body()?.let {
                    return cache.insertContacts(it.map { item -> ContactItem(item.name, item.id) })
                }
            }
        } catch (ex: ConnectException) {
            ex.printStackTrace()
            return
        } catch (ex: Exception) {
            ex.printStackTrace()
            return
        }

    }
}