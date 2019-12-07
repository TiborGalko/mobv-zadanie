package com.example.mobv_zadanie.data.db

import androidx.lifecycle.LiveData
import com.example.mobv_zadanie.data.db.model.*

class LocalCache(private val dao: ZadanieRoomDatabaseDao) {
    suspend fun insertWifiRoom(wifiRoomItem: WifiRoomItem) {
        dao.insertWifiRoom(wifiRoomItem)
    }

    suspend fun insertWifiRooms(wifiRoomItems: List<WifiRoomItem>) {
        dao.insertWifiRooms(wifiRoomItems)
    }

    suspend fun updateWifiRoom(wifiRoomItem: WifiRoomItem) {
        dao.updateWifiRoom(wifiRoomItem)
    }

    suspend fun deleteWifiRoom(wifiRoomItem: WifiRoomItem) {
        dao.deleteWifiRoom(wifiRoomItem)
    }

    fun getWifiRooms(): LiveData<List<WifiRoomItem>> {
        return dao.getWifiRooms()
    }

    fun getWifiRoomsSorted(): LiveData<List<WifiRoomItem>> {
        return dao.getWifiRoomsSorted()
    }

    // Contacts implementations
    fun getContacts(): LiveData<List<ContactItem>> {
        return dao.getContacts()
    }

    suspend fun insertContacts(contactItems: List<ContactItem>) {
        dao.insertContacts(contactItems)
    }

    suspend fun insertPosts(postItem: List<PostItem>) {
        dao.insertPosts(postItem)
    }

    suspend fun updatePost(postItem: PostItem) {
        dao.updatePost(postItem)
    }

    fun deletePost(postItem: PostItem) {
        dao.deletePost(postItem)
    }


    fun getPosts(): LiveData<List<PostItem>> {
        return dao.getPosts()
    }

    fun getPostssorted(): LiveData<List<PostItem>> {
        return dao.getPostsSorted()
    }

    fun getroomPosts(roomid:String): LiveData<List<PostItem>> {
        return dao.getroomPosts(roomid)
    }

    suspend fun insertMessage(messageItem: MessageItem) {
        dao.insertMessage(messageItem)
    }

    suspend fun insertChatMessages(messageList: List<MessageItem>) {
        dao.insertMessages(messageList)
    }

    suspend fun updateMessage(messageItem: MessageItem) {
        dao.updateMessage(messageItem)
    }

    suspend fun deleteMessage(messageItem: MessageItem) {
        dao.deleteMessage(messageItem)
    }

    fun getMessages(): LiveData<List<MessageItem>> {
        return dao.getMessages()
    }

    fun getChatsorted(): LiveData<List<MessageItem>> {
        return dao.getChatSorted()
    }

    fun getcontactchatsorted(contact:String): LiveData<List<MessageItem>> {
        return dao.getcontactchatsorted(contact)
    }

}