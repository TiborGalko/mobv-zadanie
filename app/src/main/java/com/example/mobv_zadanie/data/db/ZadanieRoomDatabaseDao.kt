package com.example.mobv_zadanie.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mobv_zadanie.data.db.model.MessageItem
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.db.model.UserItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem

@Dao
interface ZadanieRoomDatabaseDao {
    //Users
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userItem: UserItem)

    @Update
    suspend fun updateUser(userItem: UserItem)

    @Delete
    suspend fun deleteUser(userItem: UserItem)

    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<UserItem>>

    // WifiRooms
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWifiRoom(wifiRoomItem: WifiRoomItem)

    @Update
    suspend fun updateWifiRoom(wifiRoomItem: WifiRoomItem)

    @Delete
    suspend fun deleteWifiRoom(wifiRoomItem: WifiRoomItem)

    @Query("SELECT * FROM wifirooms")
    fun getWifiRooms(): LiveData<List<WifiRoomItem>>

    //Posts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(postItem: PostItem)

    @Update
    suspend fun updatePost(postItem: PostItem)

    @Delete
    suspend fun deletePost(postItem: PostItem)

    @Query("SELECT * FROM posts")
    fun getPosts(): LiveData<List<PostItem>>

    //Messages
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messageItem: MessageItem)

    @Update
    suspend fun updateMessage(messageItem: MessageItem)

    @Delete
    suspend fun deleteMessage(messageItem: MessageItem)

    @Query("SELECT * FROM messages")
    fun getMessages(): LiveData<List<MessageItem>>
}