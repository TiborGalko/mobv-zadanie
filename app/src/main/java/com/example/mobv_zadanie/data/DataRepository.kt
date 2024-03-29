package com.example.mobv_zadanie.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mobv_zadanie.data.db.LocalCache
import com.example.mobv_zadanie.data.db.model.ContactItem
import com.example.mobv_zadanie.data.db.model.MessageItem
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import com.example.mobv_zadanie.data.firebaseapi.FcmListAPI
import com.example.mobv_zadanie.data.firebaseapi.model.Data
import com.example.mobv_zadanie.data.firebaseapi.model.MessageSendRequest
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.data.webapi.CallAPI
import com.example.mobv_zadanie.data.webapi.ListAPI
import com.example.mobv_zadanie.data.webapi.model.*
import java.net.ConnectException
import java.sql.Timestamp

class DataRepository private constructor(private val cache: LocalCache, private val api: ListAPI, private val fcmAPI: FcmListAPI) {

    lateinit var uid: String

    companion object {
        const val TAG = "DataRepository"
        @Volatile
        private var INSTANCE: DataRepository ?= null

        fun getInstance(cache: LocalCache, api: ListAPI, fcmAPI: FcmListAPI): DataRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataRepository(cache, api, fcmAPI).also { INSTANCE = it }
            }
    }


    fun getWifiRoomsSorted() : LiveData<List<WifiRoomItem>> = cache.getWifiRoomsSorted(uid)
    fun getroomPosts(roomid:String) : LiveData<List<PostItem>> = cache.getroomPosts(roomid)
    fun getcontactSorted(contact:String) : LiveData<List<MessageItem>> = cache.getcontactchatsorted(contact, uid)
    suspend fun insertWifiRoom(ssid: String, time: Timestamp) = cache.insertWifiRoom(WifiRoomItem(ssid, time, uid))
    fun deletepost(postItem: PostItem) = cache.deletePost(postItem)

    fun getContacts() : LiveData<List<ContactItem>> = cache.getContacts(uid)

    // Loading rooms from api and saving to database which triggers LiveData and all that stuff
    suspend fun wifiRoomList(context: Context) {
        refresh(context)
        CallAPI.setAuthentication(true)
        try {

            val response = api.roomList(RoomListRequest(uid, CallAPI.api_key))
            if (response.isSuccessful) {
                response.body()?.let {
                    return cache.insertWifiRooms(it.map { item -> WifiRoomItem(item.roomid, item.time, uid) })
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

    suspend fun chatList(contact:String,context: Context) {
        refresh(context)
        CallAPI.setAuthentication(true)
        try {
            val response = api.chatList(MessageListRequest(uid, contact, CallAPI.api_key))
            if (response.isSuccessful) {
                response.body()?.let {
                    return cache.insertChatMessages(it.map { item ->  MessageItem(item.uid,item.contact,item.message,item.time,item.uid_name,item.contact_name,item.uid_fid,item.contact_fid) })
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

    suspend fun postList(room:String,context: Context) {
        refresh(context)
        CallAPI.setAuthentication(true)
        try {
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

    suspend fun postMessage(room:String, message:String,context: Context) {
        refresh(context)
        CallAPI.setAuthentication(true)
        try {
            api.postMessage(PostRequest(uid, room, message, CallAPI.api_key))
            postList(room, context)
        } catch (ex: ConnectException) {
            ex.printStackTrace()
            return
        } catch (ex: Exception) {
            ex.printStackTrace()
            return
        }

    }

    suspend fun postChatMessage(contact:String, message:String,context: Context) {
        refresh(context)
        CallAPI.setAuthentication(true)
        try {
            api.postChatMessage(MessageRequest(uid, contact, message, CallAPI.api_key))
            chatList(contact, context)
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
        refresh(context)
        CallAPI.setAuthentication(true)
        try {
            val response = api.contactList(ContactListRequest(uid, CallAPI.api_key))
            if (response.isSuccessful) {
                response.body()?.let {
                    return cache.insertContacts(it.map { item -> ContactItem(item.name, item.id, uid) })
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

    suspend fun register(name: String, password: String, context: Context): Int {

        val responseCode = 0
        CallAPI.setAuthentication(false)
        try {
            val response = api.userRegister(UserRequest(name, password, CallAPI.api_key))
            if (response.isSuccessful) {
                response.body()?.let {
                    SharedPrefWorker.saveString(context, "uid", response.body()!!.uid)
                    SharedPrefWorker.saveString(context, "access", response.body()!!.access)
                    SharedPrefWorker.saveString(context, "refresh", response.body()!!.refresh)
                    //save current user name, password
                    SharedPrefWorker.saveString(context, "name", name)
                    SharedPrefWorker.saveString(context, "password", password)
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

    suspend fun login(name: String, password: String, context: Context): Int {

        val responseCode = 0
        CallAPI.setAuthentication(false)
        try {
            val response = api.userLogin(UserRequest(name, password, CallAPI.api_key))
            Log.i("TAG_API", "login DATAREP response:" +response.code() )
            if (response.isSuccessful) {
                response.body()?.let {
                    uid = response.body()!!.uid
                    SharedPrefWorker.saveString(context, "uid", response.body()!!.uid)
                    SharedPrefWorker.saveString(context, "access", response.body()!!.access)
                    SharedPrefWorker.saveString(context, "refresh", response.body()!!.refresh)
                    //save current user name, password
                    SharedPrefWorker.saveString(context, "name", name)
                    SharedPrefWorker.saveString(context, "password", password)
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

    suspend fun refresh(context: Context){
        val refresh = SharedPrefWorker.getString(context,"refresh", "").toString()
        try {
        val response = api.userRefresh(UserRefresh(uid,refresh,CallAPI.api_key))
        if (response.isSuccessful) {
            response.body()?.let {
                uid = response.body()!!.uid
                SharedPrefWorker.saveString(context, "uid", response.body()!!.uid)
                SharedPrefWorker.saveString(context, "access", response.body()!!.access)
                SharedPrefWorker.saveString(context, "refresh", response.body()!!.refresh)
                //save current user name, password
            }
        }
        } catch (ex: ConnectException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    // Clears shared preferences and uid
    fun logout(context: Context) {
        uid = ""

        SharedPrefWorker.saveString(context, "uid", "")
        SharedPrefWorker.saveString(context, "access", "")
        SharedPrefWorker.saveString(context, "refresh", "")
        //save current user name, password
        SharedPrefWorker.saveString(context, "name", "")
        SharedPrefWorker.saveString(context, "password", "")
    }

    // Used to post new firebase token to
    suspend fun postFirebaseId(fid: String) {
        CallAPI.setAuthentication(true)
        try {
            api.postUserFid(UserFidRequest(uid, fid, CallAPI.api_key))
        } catch (ex: ConnectException) {
            ex.printStackTrace()
            return
        } catch (ex: Exception) {
            ex.printStackTrace()
            return
        }
    }

    suspend fun postFcmMessage(topic: String, message: String, senderName: String) {
        try {
            // There is difference if you use /topics/ and
            if (senderName.contains("Room")) {
                fcmAPI.postFcmMessage(
                    MessageSendRequest(
                        "/topics/$topic", Data(
                            message, "Nová správa od $senderName"
                        )
                    )
                )
            } else {
                fcmAPI.postFcmMessage(
                    MessageSendRequest( topic, Data( message, "Nová správa od $senderName"))
                )
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