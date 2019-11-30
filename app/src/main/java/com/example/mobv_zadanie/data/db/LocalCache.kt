package com.example.mobv_zadanie.data.db

import androidx.lifecycle.LiveData
import com.example.mobv_zadanie.data.db.model.MessageItem
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.db.model.UserItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem

class LocalCache(private val dao: ZadanieRoomDatabaseDao) {
    suspend fun insertUser(userItem: UserItem) {
        dao.insertUser(userItem)
    }

    suspend fun updateUser(userItem: UserItem) {
        dao.updateUser(userItem)
    }

    suspend fun deleteUser(userItem: UserItem) {
        dao.deleteUser(userItem)
    }

    fun getUsers(): LiveData<List<UserItem>> {
        return dao.getUsers()
    }

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

    suspend fun insertPost(postItem: PostItem) {
        dao.insertPost(postItem)
    }

    suspend fun updatePost(postItem: PostItem) {
        dao.updatePost(postItem)
    }

    suspend fun deletePost(postItem: PostItem) {
        dao.deletePost(postItem)
    }

    fun getPosts(): LiveData<List<PostItem>> {
        return dao.getPosts()
    }

    suspend fun insertMessage(messageItem: MessageItem) {
        dao.insertMessage(messageItem)
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
}