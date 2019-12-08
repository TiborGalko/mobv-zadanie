package com.example.mobv_zadanie.data

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import com.airbnb.lottie.LottieAnimationView
import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.db.LocalCache
import com.example.mobv_zadanie.data.db.model.ContactItem
import com.example.mobv_zadanie.data.db.model.MessageItem
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.data.webapi.CallAPI
import com.example.mobv_zadanie.data.webapi.ListAPI
import com.example.mobv_zadanie.data.webapi.model.*
import com.example.mobv_zadanie.ui.LoginFragment
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    fun getPostsSorted() : LiveData<List<PostItem>> = cache.getPostssorted()
    fun getChatSorted() : LiveData<List<MessageItem>> = cache.getChatsorted()
    fun getroomPosts(roomid:String) : LiveData<List<PostItem>> = cache.getroomPosts(roomid)
    fun getcontactSorted(contact:String) : LiveData<List<MessageItem>> = cache.getcontactchatsorted(contact)
    suspend fun insertWifiRoom(wifiRoomItem: WifiRoomItem) = cache.insertWifiRoom(wifiRoomItem)
    suspend fun updateWifiRoom(wifiRoomItem: WifiRoomItem) = cache.updateWifiRoom(wifiRoomItem)
    fun deletepost(postItem: PostItem) = cache.deletePost(postItem)

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

    suspend fun chatList(context: Context, contact:String) {
        CallAPI.setAuthentication(true)
        try {
            val uid: String = SharedPrefWorker.getString(context, "uid", "not_found").toString()
            if (uid == "not_found") {
                return
            }
            val response = api.chatList(MessageListRequest(uid, contact, CallAPI.api_key))
            if (response.isSuccessful) {
                response.body()?.let {
                    return cache.insertChatMessages(it.map { item -> MessageItem(item.uid,item.contact,item.message,item.time,item.uid_name,item.contact_name,item.uid_fid,item.contact_fid) })
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

    suspend fun postList(context: Context, room:String) {
        CallAPI.setAuthentication(true)
        try {
            val uid: String = SharedPrefWorker.getString(context, "uid", "not_found").toString()
            if (uid == "not_found") {
                return
            }
            val response = api.postList(PostListRequest(uid, room, CallAPI.api_key))
            if (response.isSuccessful) {
                response.body()?.let {
                    return cache.insertPosts(it.map { item -> PostItem(item.uid, item.roomid, item.message, item.time, item.name) })
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

    suspend fun postMessage(context: Context, room:String, message:String) {
        CallAPI.setAuthentication(true)
        try {
            val uid: String = SharedPrefWorker.getString(context, "uid", "not_found").toString()
            if (uid == "not_found") {
                return
            }
            api.postMessage(PostRequest(uid, room, message, CallAPI.api_key))
        } catch (ex: ConnectException) {
            ex.printStackTrace()
            return
        } catch (ex: Exception) {
            ex.printStackTrace()
            return
        }

    }

    suspend fun postChatMessage(context: Context, contact:String, message:String) {
        CallAPI.setAuthentication(true)
        try {
            val uid: String = SharedPrefWorker.getString(context, "uid", "not_found").toString()
            if (uid == "not_found") {
                return
            }
            api.postChatMessage(MessageRequest(uid, contact, message, CallAPI.api_key))
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

    suspend fun register(name: String, password: String): Int {

        val responseCode = 0
        CallAPI.setAuthentication(false)
        try {
            val response = api.userRegister(UserRequest(name, password, CallAPI.api_key))
            Log.i("TAG_API", "DATAREP response code: " + response.code())
            if (response.isSuccessful) {
                response.body()?.let {
                }
            }
            return response.code()
        } catch (ex: ConnectException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return responseCode
    }

}