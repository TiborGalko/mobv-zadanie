package com.example.mobv_zadanie.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mobv_zadanie.data.db.LocalCache
import com.example.mobv_zadanie.data.db.model.ContactItem
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.data.webapi.CallAPI
import com.example.mobv_zadanie.data.webapi.ListAPI
import com.example.mobv_zadanie.data.webapi.model.ContactListRequest
import com.example.mobv_zadanie.data.webapi.model.RoomListRequest
import com.example.mobv_zadanie.data.webapi.model.RoomListResponse
import com.example.mobv_zadanie.data.webapi.model.RoomMessagesListRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import kotlin.math.log

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
    fun getRoomPostsSorted() : LiveData<List<PostItem>> = cache.getRoomPostsSorted()
    suspend fun insertWifiRoom(wifiRoomItem: WifiRoomItem) = cache.insertWifiRoom(wifiRoomItem)
    suspend fun updateWifiRoom(wifiRoomItem: WifiRoomItem) = cache.updateWifiRoom(wifiRoomItem)

    fun getContacts() : LiveData<List<ContactItem>> = cache.getContacts()

    // Loading rooms from api and saving to database which triggers LiveData and all that stuff
    suspend fun wifiRoomList(context: Context) {
        Log.i("TAG_API", "wifiroom")
        CallAPI.setAuthentication(true)
        CallAPI.type.roomList(
            RoomListRequest(
                SharedPrefWorker.getString(context, "uid", "not_found").toString(),
                CallAPI.api_key
            )
        ).enqueue(object: Callback<List<RoomListResponse>> {
            override fun onFailure(call: Call<List<RoomListResponse>>, t: Throwable) {
                //Toast.makeText(activity, "Oops, something went wrong: "+t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<RoomListResponse>>, response: Response<List<RoomListResponse>>) {
                Log.i("TAG_API", "API response code: "+response.code())

                if (response.code() == 200){

                }
                else {

                }
            }
        })


//        Log.i("TAG_API", "wifiroomlist called")
//        CallAPI.setAuthentication(true)
//        try {
//            val uid: String = SharedPrefWorker.getString(context, "uid", "not_found").toString()
//            if (uid == "not_found") {
//                return
//            }
//            val response = api.roomList(RoomListRequest(uid, CallAPI.api_key))
//            Log.i("TAG_API", response.code().toString())
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    return cache.insertWifiRooms(it.map { item -> WifiRoomItem(item.roomId, item.time) })
//                }
//            }
//        } catch (ex: ConnectException) {
//            ex.printStackTrace()
//            return
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            return
//        }

    }





    suspend fun PostsList(context: Context, room:String) {
        CallAPI.setAuthentication(true)
        try {
            val uid: String = SharedPrefWorker.getString(context, "uid", "not_found").toString()
            if (uid == "not_found") {
                return
            }
            val response = api.messagesList(RoomMessagesListRequest(uid,room,CallAPI.api_key))
            if (response.isSuccessful) {
                response.body()?.let {

                    return cache.insertPosts(it.map { item -> PostItem(item.uid, item.roomid, item.message,item.name, item.time) })
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